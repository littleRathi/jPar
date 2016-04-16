package de.bs.cli.jpar.extractor.type;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.config.ExceptionMessages;
import de.bs.cli.jpar.extractor.ExtractedOption;
import de.bs.cli.jpar.extractor.ExtractedArguments;
import de.bs.cli.jpar.extractor.HelpOption;
import de.bs.cli.jpar.process.Parameters;

public abstract class Type implements ExceptionMessages {

	private Class<?> targetType;
	private ExtractedOption option;
	private ExtractedArguments arguments;
	
	public Type(final Class<?> targetType, final ExtractedOption option, final ExtractedArguments arguments) {
		if (option == null) {
			throw new JParException(EXC_TYPE_MISSING_OPTION_ANNOTATION, targetType);
		}
		if (targetType == null) {
			throw new JParException(EXC_TYPE_TARGET_TYPE_IS_NULL, option.getOptionName());
		}
		this.targetType = targetType;
		this.option = option;
		this.arguments = arguments;
	}
	
	public ExtractedOption getOption() {
		return option;
	}
	public ExtractedArguments getArguments() {
		return arguments;
	}
	public Class<?> getTargetType() {
		return targetType;
	}
	
	public static Type getTypeProcessor(final Class<?> type, final ExtractedOption option, final ExtractedArguments arguments) {
		if (type == Void.class && option.getClass() == HelpOption.class) {
			return new VoidType(option, arguments);
		}
		if ((type == void.class || type == Void.class)) {
			throw new JParException(EXC_TYPE_NOT_SUPPORTED, type);
		}
		if (type == Boolean.class || type == boolean.class) {
			return new BooleanType(option, arguments);
		}
//		if (type == String.class) {
//			return new StringType(option, arguments);
//		}
		if (Collection.class.isAssignableFrom(type)) {
			return new CollectionType(type, option, arguments);
		}
		if (option.getOption().sourceType() == Class.class) {
			return new ClassObjectType(type, option, arguments);	
		}
		if (checkClassForStringInstanziateMethods(type)) {
			return new StringObjectType(type, option, arguments);
		}
		throw new JParException(EXC_TYPE_UNSUPPORTED_YET, type);
	}
	
	public abstract Object processArgs(final String option, final String argument, final Parameters args);

	public abstract String getShortDescription();
	public abstract void getManualDescription(final StringBuilder descriptionBuilder);


	protected static void checkClassForStringInstanziateMethodsOrThrow(final Class<?> type) {
		if (!checkClassForStringInstanziateMethods(type)) {
			throw new JParException(EXC_TYPE_NO_STRING_INSTANZIATE_METHOD, type);
		}
	}
	
	protected static boolean checkClassForStringInstanziateMethods(final Class<?> type) {
		Method valueOf = null;
		
		try {
			valueOf = type.getMethod("valueOf", String.class);
			return true;
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		}
		
		if (valueOf == null) {
//			Constructor<?> con = null;
			try {
				type.getConstructor(String.class);
				return true;
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			}
		}
		return false;
	}
	
	protected static Object castTo(final Class<?> newType, final String value) {
		if (String.class.equals(newType)) {
			return value;
		}

		Object result = castWithValueOf(newType, value);
		
		if (result == null) {
			result = castWithConstructor(newType, value);
		}
		
		if (result == null) {
			throw new JParException(EXC_TYPE_NEEDED_CONSTRUCTOR, newType);
		}
		
		return result;
	}
	
	private static Object castWithConstructor(final Class<?> newType, final String value) {
		try {
			Constructor<?> con = newType.getConstructor(String.class);
			return con.newInstance(value);
		} catch (NoSuchMethodException e) {
		} catch (Exception e) {
		}
		return null;
	}
	
	private static Object castWithValueOf(final Class<?> newType, final String value) {
		try {
			Method valueOf = newType.getMethod("valueOf", String.class);
			
			if (value != null && Modifier.isStatic(valueOf.getModifiers())) {
				return valueOf.invoke(null, value);
			}
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		return null;
	}
	
	protected void createValuesDescription(final StringBuilder result, final boolean multiple) {
		if (getArguments() != null) {
			String[][] values = getArguments().getValues();
			
			if (values != null && values.length > 0) {
				if (multiple) {
					if (values.length == 1) {
						result.append(" - following values are valid ");
					} else {
						result.append(" - following values from one of the groups are valid ");
					}
				} else {
					result.append("- one of the following ");
				}
				for (int i = 0; i < values.length; i++) {
					String[] subValues = values[i];
					
					createValuesDescriptionForSublist(subValues, result, multiple);
					if (i < values.length - 1) {
						result.append("or ");
					}
				}
			}
		}
	}
	
	private void createValuesDescriptionForSublist(final String[] subValues, final StringBuilder result, final boolean multiple) {
		result.append("(");
		if (subValues.length > 1) {
			result.append(subValues[0]);
			for (int j = 0; j < subValues.length; j++) {
				result.append(", ").append(subValues[j]);
			}
		} else if (subValues.length == 1) {
			result.append("single option " + subValues[0]);
		}
		result.append(") ");
	}
}
