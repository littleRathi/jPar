package de.bs.cli.jpar.examples;

import java.util.List;

import de.bs.cli.jpar.Arguments;
import de.bs.cli.jpar.CliProgram;
import de.bs.cli.jpar.Option;

@CliProgram(name=WorkingFieldsExample.PROG_NAME, description=WorkingFieldsExample.PROG_DESC)
public class WorkingFieldsExample {
	public static final String PROG_NAME = "WorkingFieldsExample";
	public static final String PROG_DESC = "Good description for the program.";
	
	public static final String STRING_VALUE_A = "fieldAvalue";
	public static final String STRING_VALUE_B = "fieldBvalue";
	public static final String STRING_VALUE_C = "fieldCvalue";
	
	public static final String OPT_BOOL_FIELD = "boolTest";
	public static final String OPT_CLASS_FIELD = "classField";
	public static final String OPT_STRING_FIELD = "stringField";
	public static final String OPT_LIST_INT_FIELD = "listIntField";
	public static final String OPT_LIST_STRING_FIELD = "listStringField";
	
	public static final String OPT_BOOL_DESC = "Text for option boolTest...";
	public static final String OPT_CLASS_DESC = "Text for option classField";
	public static final String OPT_STRING_DESC = "Text for option stringField";
	public static final String OPT_LIST_INT_DESC = "Text for option listField";
	public static final String OPT_LIST_STRING_DESC = "Text for listStringField";
	
	public static final Class<?> OPT_LIST_INT_TYPE = Integer.class;
	public static final Class<?> OPT_LIST_STRING_TYPE = String.class;
	
	public static final String LIST_INT_DELIMITER = ",";
	
	@Option(name=OPT_BOOL_FIELD, description=OPT_BOOL_DESC)
	private boolean boolField;
	
	@Option(name=OPT_CLASS_FIELD, description=OPT_CLASS_DESC, sourceType=Class.class, required=true)
	private WorkingFieldsExample classField;
	
	@Option(name=OPT_STRING_FIELD, description=OPT_STRING_DESC, required=true)
	private String stringField;
	
	@Option(name=OPT_LIST_INT_FIELD, description=OPT_LIST_INT_DESC, sourceType=Integer.class)
	@Arguments(delimiter=LIST_INT_DELIMITER)
	private List<Integer> listIntField;
	
	@Option(name=OPT_LIST_STRING_FIELD, description=OPT_LIST_STRING_DESC, sourceType=String.class)
	private List<String> listStringField;
	
	@Arguments(name=OPT_LIST_STRING_FIELD)
	public static String[][] getValidStringValues() {
		return new String[][]{{STRING_VALUE_A, STRING_VALUE_B, STRING_VALUE_C}};
	}
}
