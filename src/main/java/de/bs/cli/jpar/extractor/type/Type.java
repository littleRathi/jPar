package de.bs.cli.jpar.extractor.type;

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
		if (type == String.class) {
			return new StringType(option, arguments);
		}
		if (Collection.class.isAssignableFrom(type)) {
			return new CollectionType(type, option, arguments);
		}
		if (option.getOption().sourceType() == Class.class) {
			return new ClassObjectType(type, option, arguments);	
		}
		throw new JParException(EXC_TYPE_UNSUPPORTED, type);
	}
	public abstract Object processArgs(final String option, final String argument, final Parameters args);

	public abstract String getShortDescription();
	public abstract void getManualDescription(final StringBuilder descriptionBuilder);

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
