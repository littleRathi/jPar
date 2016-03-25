package de.bs.cli.jpar.examples;

import java.util.List;

import de.bs.cli.jpar.Arguments;
import de.bs.cli.jpar.CliProgram;
import de.bs.cli.jpar.Option;
import de.bs.cli.jpar.Values;

@CliProgram(name=WorkingMethodsExample.PROG_NAME, 
	description=WorkingMethodsExample.PROG_DESC)
public class WorkingMethodsExample {
	public static final String PROG_NAME = "WorkingFieldsExample";
	public static final String PROG_DESC = "good description for the program.";
	
	public static final String STRING_LIST_VALUE_A = "methodAvalue";
	public static final String STRING_LIST_VALUE_B = "methodBvalue";
	public static final String STRING_LIST_VALUE_C = "methodCvalue";
	
	public static final String STRING_VALUE_A = "simpleStringA";
	public static final String STRING_VALUE_B = "simpleStringB";
	public static final String STRING_VALUE_C = "simpleStringC";
	
	public static final String OPT_BOOL_METHOD = "boolMethod";
	public static final String OPT_CLASS_METHOD = "classMethod";
	public static final String OPT_STRING_METHOD = "stringMethod";
	public static final String OPT_LIST_INT_METHOD = "listIntMethod";
	public static final String OPT_LIST_STRING_METHOD = "listStringMethod";
	
	public static final String OPT_BOOL_DESC = "Text for option boolMethod...";
	public static final String OPT_CLASS_DESC = "Text for option classMethod...";
	public static final String OPT_STRING_DESC = "Text for option stringMethod";
	public static final String OPT_LIST_INT_DESC = "Text for option listIntMethod...";
	public static final String OPT_LIST_STRING_DESC = "Text for option listStringMethod...";
	
	public static final Class<?> OPT_LIST_INT_TYPE = Integer.class;
	public static final Class<?> OPT_LIST_STRING_TYPE = String.class;
	
	@Option(name=OPT_BOOL_METHOD, description=OPT_BOOL_DESC, required=true)
	public void setBoolMethod(final boolean boolValue) {
	}
	
	@Option(name=OPT_CLASS_METHOD, description=OPT_CLASS_DESC, sourceType=Class.class)
	public void setClassMethod(final WorkingMethodsExample objectInstance) {
	}

	@Option(name=OPT_STRING_METHOD, description=OPT_STRING_DESC)
	@Arguments(values={STRING_VALUE_A, STRING_VALUE_B, STRING_VALUE_C})
	public void setStringMethod(final String stringValue) {
	}
	
	@Option(name=OPT_LIST_INT_METHOD, description=OPT_LIST_INT_DESC, sourceType=Integer.class, required=true)
	@Arguments(values={STRING_LIST_VALUE_A, STRING_LIST_VALUE_B, STRING_LIST_VALUE_C})
	public void setListIntMethod(final List<Integer> intList) {
	}
	
	@Option(name=OPT_LIST_STRING_METHOD, description=OPT_LIST_STRING_DESC, sourceType=String.class)
	public void setListStringMethod(final List<String> stringList) {
	}
	
	@Arguments(name=OPT_LIST_STRING_METHOD) 
	public static String[][] getValidStringValues() {
		return Values.createGroups(Values.createSimpleValueList(STRING_LIST_VALUE_A, STRING_LIST_VALUE_B, STRING_LIST_VALUE_C));
	}
}
