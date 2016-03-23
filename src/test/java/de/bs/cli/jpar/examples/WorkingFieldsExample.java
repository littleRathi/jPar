package de.bs.cli.jpar.examples;

import java.util.List;

import de.bs.cli.jpar.Arguments;
import de.bs.cli.jpar.CliProgram;
import de.bs.cli.jpar.Option;

@CliProgram(name=WorkingFieldsExample.PROG_NAME, description=WorkingFieldsExample.PROG_DESC)
public class WorkingFieldsExample {
	public static final String PROG_NAME = "WorkingFieldsExample";
	public static final String PROG_DESC = "good description for the program.";
	
	public static final String STRING_VALUE_A = "a";
	public static final String STRING_VALUE_B = "b";
	public static final String STRING_VALUE_C = "c";
	
	public static final String OPT_BOOL_FIELD = "boolTest";
	public static final String OPT_CLASS_FIELD = "classField";
	public static final String OPT_STRING_FIELD = "stringField";
	public static final String OPT_LIST_INT_FIELD = "listIntField";
	public static final String OPT_LIST_STRING_FIELD = "listStringField";
	
	@Option(name=OPT_BOOL_FIELD, description="Text for option boolTest...")
	private boolean boolField;
	
	@Option(name=OPT_CLASS_FIELD, description="Text for option classField", sourceType=Class.class, required=true)
	private WorkingFieldsExample classField;
	
	@Option(name=OPT_STRING_FIELD, description="Text for option stringField", required=true)
	private String stringField;
	
	@Option(name=OPT_LIST_INT_FIELD, description="Text for option listField", sourceType=Integer.class)
	@Arguments(delimiter=",")
	private List<Integer> listIntField;
	
	@Option(name=OPT_LIST_STRING_FIELD, description="Text for listStringField", sourceType=String.class)
	private List<String> listStringField;
	
	@Arguments(name=OPT_LIST_STRING_FIELD)
	public static String[][] getValidStringValues() {
		return new String[][]{{STRING_VALUE_A, STRING_VALUE_B, STRING_VALUE_C}};
	}
}
