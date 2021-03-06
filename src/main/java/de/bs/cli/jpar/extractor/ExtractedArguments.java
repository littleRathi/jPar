package de.bs.cli.jpar.extractor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.bs.cli.jpar.Option;
import de.bs.cli.jpar.config.Defaults;
import de.bs.cli.jpar.config.ExceptionMessages;
import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.Arguments;

public class ExtractedArguments implements ExceptionMessages {
	private Arguments arguments;
	private String[][] values;
	private List<Set<String>> valuesSet = new LinkedList<Set<String>>();
	
	private ExtractedArguments(final String[][] values, final Arguments arguments) {
		this.values = values;
		this.arguments = arguments;
		
		boolean hasValues = false;
		for (String[] subValues: values) {
			Set<String> subSet = new HashSet<String>();
			for (String value: subValues) {
				subSet.add(value);
				hasValues = true;
			}
			valuesSet.add(subSet);
		}
		if (!hasValues) {
			valuesSet = null;
		}
	}
	public String getDelimiter() {
		return (arguments.delimiter() != null && !arguments.delimiter().isEmpty() ? arguments.delimiter() : Defaults.getListDelimiter());
	}
	public String[][] getValues() {
		return values;
	}
	
	public boolean validValue(final String value) {
		boolean result = false;
		for (Set<String> valueSet: valuesSet) {
			if (valueSet.contains(value)) {
				result = true;
				break;
			}
		}
		return result;
	}
	public boolean validValues(final String[] values) {
		if (values == null || values.length == 0) {
			return false;
		}
		if (valuesSet == null) {
			return true;
		}
		boolean valid = false;
		
		for (Set<String> valueSet: valuesSet) {
			if (validInSubValues(values, valueSet)) {
				valid = true;
				break;
			}
		}
		
		return valid;
	}
	private boolean validInSubValues(final String[] values, final Set<String> valueSet) {
		boolean valid = true;
		
		for (String value: values) {
			if (!valueSet.contains(value)) {
				valid = false;
				break;
			}
		}
		
		return valid;
	}
	
	private static String[][] extractValuesFromArgumentValues(final Arguments arguments, final String optionName) {
		if (arguments.name() != null && !arguments.name().isEmpty()) {
			throw new JParException(EXC_EXTRACTOR_VALUES_NO_NAME, optionName);
		}
		
		return new String[][]{arguments.values()};
	}

//	TODO[next version] handling of the field: allowed should be:  first only array
//	Array, Collection (List, Set, ...)
//		=> get Type and try to transform, if error, then the is the type wrong
//	internal transformation to a internal representation from String[][] Validate <-(ValidateValues|ValidateValue)
	private static String[][] extractValuesFromField(final Arguments arguments, final String optionName, final Field field) {
		preConditionExtractValues(arguments, optionName, field.getType(), field.getModifiers(), field.toString());

		String[][] values = null;
		try {
			field.setAccessible(true);
			values = (String[][])field.get(null);
		} catch (Exception e) {
			throw new JParException(EXC_EXTRACTOR_VALUES_GET_VALUES, field.toString(), optionName);
		}
		
		postConditionExtractValues(values, field.toString());

		return values;
	}
	
	private static String[][] extractValuesFromMethod(final Arguments arguments, final String optionName, final Method method) {
		preConditionExtractValues(arguments, optionName, method.getReturnType(), method.getModifiers(), method.toString());
		
		Class<?>[] parameters = method.getParameterTypes();
		if (parameters != null && parameters.length > 0) {
			throw new JParException(EXC_EXTRACTOR_VALUES_METHOD_NO_PARAMETERS, method.toString(), parameters.length);
		}

		String[][] values = null;
		method.setAccessible(true);
		try {
			values = (String[][]) method.invoke(null);
		} catch (Exception e) {
			throw new JParException(EXC_EXTRACTOR_VALUES_GET_VALUES, method.toString(), optionName);
		}

		postConditionExtractValues(values, method.toString());
		
		return values;
	}
	
	private static void postConditionExtractValues(final String[][] values, final String element) {
		if (values == null) {
			throw new JParException(EXC_EXTRACTOR_VALUES_IS_NULL, element);
		}
		if (values.length == 0) {
			throw new JParException(EXC_EXTRACTOR_VALUES_MISSING_GROUPS, element);
		}
		if (values[0].length == 0) {
			throw new JParException(EXC_EXTRACTOR_VALUES_MISSING_CONTENT, element);
		}
	}
	
	private static void preConditionExtractValues(final Arguments arguments, final String optionName, final Class<?> valueType, final int modifiers, final String element) {
		if (arguments.values() != null && arguments.values().length > 0) {
			throw new JParException(EXC_EXTRACTOR_NO_VALUES_ALLOWED, element, optionName);
		}
		if (arguments.name() == null || arguments.name().isEmpty()) {
			throw new JParException(EXC_EXTRACTOR_VALUES_NEED_NAME, element);
		}
		if (!Modifier.isStatic(modifiers)) {
			throw new JParException(EXC_EXTRACTOR_VALUES_ELEMENT_NOT_STATIC, element);
		}
		if (!valueType.isArray()) {
			throw new JParException(EXC_EXTRACTOR_VALUES_WRONG_TYPE, element);
		}
	}
	
	public static ExtractedArguments getAnnotationOnField(final Arguments arguments, final Option option, final String optionName, final Field field) {
		String[][] values = null;
		if (option != null) {
			values = extractValuesFromArgumentValues(arguments, optionName);
		} else {
			values = extractValuesFromField(arguments, optionName, field);
		}
		return createExtractedArguments(values, arguments);
	}
	
	public static ExtractedArguments getAnnotationOnMethod(final Arguments arguments, final Option option, final String optionName, final Method method) {
		String[][] values = null;
		if (option != null) {
			values = extractValuesFromArgumentValues(arguments, optionName);
		} else {
			values = extractValuesFromMethod(arguments, optionName, method);
		}
		return createExtractedArguments(values, arguments);
	}
	
	private static ExtractedArguments createExtractedArguments(final String[][] values, final Arguments arguments) {
		ExtractedArguments extractedValues = new ExtractedArguments(values, arguments);
		return extractedValues;
	}
}
