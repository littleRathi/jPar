package de.bs.cli.jpar.examples;

import java.util.List;

import de.bs.cli.jpar.Arguments;
import de.bs.cli.jpar.CliProgram;
import de.bs.cli.jpar.Option;
import de.bs.cli.jpar.Values;

@CliProgram(name=WorkingMethodsExample.PROG_NAME, 
	description=WorkingMethodsExample.PROG_DESC,
	signature=WorkingMethodsExample.PROG_SIG)
public class WorkingMethodsExample {
	public static final String PROG_NAME = "WorkingFieldsExample";
	public static final String PROG_DESC = "good description for the program.";
	public static final String PROG_SIG = "best sig (c)(r) ever...";
	
	public static final String STRING_VALUE_A = "a";
	public static final String STRING_VALUE_B = "b";
	public static final String STRING_VALUE_C = "c";
	
	public static final String OPT_BOOL_METHOD = "boolMethod";
	public static final String OPT_CLASS_METHOD = "classMethod";
	public static final String OPT_STRING_METHOD = "stringMethod";
	public static final String OPT_LIST_INT_METHOD = "listIntMethod";
	public static final String OPT_LIST_STRING_METHOD = "listStringMethod";
	
	@Option(name=OPT_BOOL_METHOD, description="Text for option boolMethod...", required=true)
	public void setBoolMethod(final boolean boolValue) {
	}
	
	@Option(name=OPT_CLASS_METHOD, description="Text for option classMethod...", sourceType=Class.class)
	public void setClassMethod(final WorkingMethodsExample objectInstance) {
	}

	@Option(name=OPT_STRING_METHOD, description="Text for option stringMethod")
	public void setStringMethod(final String stringValue) {
	}
	
	@Option(name=OPT_LIST_INT_METHOD, description="Text for option listIntMethod...", sourceType=Integer.class, required=true)
	@Arguments(values={"1", "2", "3"})
	public void setListIntMethod(final List<Integer> intList) {
	}
	
	@Option(name=OPT_LIST_STRING_METHOD, description="Text for option listStringMethod...", sourceType=String.class)
	public void setListStringMethod(final List<String> stringList) {
	}
	
	@Arguments(name=OPT_LIST_STRING_METHOD) 
	public static String[][] getValidStringValues() {
		return Values.createGroups(Values.createSimpleValueList(STRING_VALUE_A, STRING_VALUE_B, STRING_VALUE_C));
	}
}
