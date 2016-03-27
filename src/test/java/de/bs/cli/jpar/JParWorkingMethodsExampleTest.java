package de.bs.cli.jpar;

import static org.junit.Assert.fail;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import static de.bs.cli.jpar.config.Defaults.getListDelimiter;
import static de.bs.cli.jpar.config.Defaults.getOptionDelimiter;
import static de.bs.cli.jpar.examples.WorkingFieldsExample.OPT_CLASS_FIELD;
import static de.bs.cli.jpar.examples.WorkingFieldsExample.OPT_STRING_FIELD;
import static de.bs.cli.jpar.examples.WorkingMethodsExample.INT_LIST_VALUE_1;
import static de.bs.cli.jpar.examples.WorkingMethodsExample.INT_LIST_VALUE_2;
import static de.bs.cli.jpar.examples.WorkingMethodsExample.INT_LIST_VALUE_3;
import static de.bs.cli.jpar.examples.WorkingMethodsExample.OPT_BOOL_METHOD;
import static de.bs.cli.jpar.examples.WorkingMethodsExample.OPT_CLASS_METHOD;
import static de.bs.cli.jpar.examples.WorkingMethodsExample.OPT_LIST_INT_METHOD;
import static de.bs.cli.jpar.examples.WorkingMethodsExample.OPT_LIST_STRING_METHOD;
import static de.bs.cli.jpar.examples.WorkingMethodsExample.OPT_STRING_METHOD;
import static de.bs.cli.jpar.examples.WorkingMethodsExample.STRING_LIST_VALUE_A;
import static de.bs.cli.jpar.examples.WorkingMethodsExample.STRING_LIST_VALUE_B;
import static de.bs.cli.jpar.examples.WorkingMethodsExample.STRING_VALUE_A;
import static de.bs.cli.jpar.extractor.ExtractedOption.asOptionName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.examples.WorkingMethodsExample;
import de.bs.cli.jpar.extractor.type.BooleanType;
import de.bs.cli.jpar.process.JParProcess;

public class JParWorkingMethodsExampleTest {
	private static final String boolValue = BooleanType.TRUE;
	private static final String classValue = "de.bs.cli.jpar.examples.WorkingMethodsExample";
	private static final String stringValue = STRING_VALUE_A;
	private static final String listIntValue = INT_LIST_VALUE_1 + getListDelimiter() + INT_LIST_VALUE_3 + getListDelimiter() + INT_LIST_VALUE_2;
	private static final String listStringValue = STRING_LIST_VALUE_A + getListDelimiter() + STRING_LIST_VALUE_B;
	
	private WorkingMethodsExample test;
	
	@Before
	public void setupTest() {
		test = new WorkingMethodsExample();
	}
	
	@After
	public void teardownTest() {
		test = null;
	}
	
	@Test(expected=JParException.class)
	public void testNoArgs() {
		JPar.process(test, null);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testNoProgram() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_LIST_INT_METHOD, listIntValue)
		};
		
		preValidation();
		
		JPar.process(null, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testOnlyRequiredOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_LIST_INT_METHOD, listIntValue)
		};
		
		preValidation();
		
		JPar.process(null, args);
		
		assertThat(test.isTmpBoolValue(), equalTo(true));
		assertThat(test.getTmpIntList(), notNullValue());
		assertThat(test.getTmpIntList(), hasItems(Integer.valueOf(INT_LIST_VALUE_1), Integer.valueOf(INT_LIST_VALUE_2), Integer.valueOf(INT_LIST_VALUE_3)));
	}
	
	@Test
	public void testAllOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_CLASS_METHOD, classValue),
				createPar(OPT_STRING_METHOD, stringValue),
				createPar(OPT_LIST_INT_METHOD, listIntValue),
				createPar(OPT_LIST_STRING_METHOD, listStringValue)
		};
		
		preValidation();
		
		JPar.process(test, args);
		
		assertThat(test.isTmpBoolValue(), equalTo(true));
		
		assertThat(test.getTmpObjectInstance(), notNullValue());
		assertThat(test.getTmpObjectInstance().getClass().getName(), equalTo(classValue));
		
		assertThat(test.getTmpStringValue(), equalTo(stringValue));
		
		List<Integer> intList = test.getTmpIntList();
		assertThat(intList, notNullValue());
		assertThat(test.getTmpIntList(), hasItems(Integer.valueOf(INT_LIST_VALUE_1), Integer.valueOf(INT_LIST_VALUE_2), Integer.valueOf(INT_LIST_VALUE_3)));
		
		List<String> stringList = test.getTmpStringList();
		assertThat(stringList, notNullValue());
		assertThat(stringList, hasItems(STRING_LIST_VALUE_A, STRING_LIST_VALUE_B));
	}
	
	@Test(expected=JParException.class)
	public void testWrongBoolWithRequiredOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, "abcdefghijklmnopqrstuvwxyz"),
				createPar(OPT_LIST_INT_METHOD, listIntValue)
		};
		
		preValidation();
		
		JPar.process(null, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testWrongClassWithRequiredOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_CLASS_METHOD, "abcdefghijklmnopqrstuvwxyz"),
				createPar(OPT_LIST_INT_METHOD, listIntValue),
		};
		
		preValidation();
		
		JPar.process(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testWrongStringWithRequiredOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_STRING_METHOD, "abcdefghijklmnopqrstuvwxyz"),
				createPar(OPT_LIST_INT_METHOD, listIntValue),
		};
		
		preValidation();
		
		JPar.process(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testWrongListIntWithRequiredOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_LIST_INT_METHOD, "abcdefghijklmnopqrstuvwxyz"),
		};
		
		preValidation();
		
		JPar.process(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testWrongListStringWithRequiredOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_LIST_INT_METHOD, listIntValue),
				createPar(OPT_LIST_STRING_METHOD, "abcdefghijklmnopqrstuvwxyz")
		};
		
		preValidation();
		
		JPar.process(test, args);
		
		fail();
	}
	
	private void preValidation() {
		assertThat(test.isTmpBoolValue(), equalTo(false));
		assertThat(test.getTmpStringValue(), nullValue());
		assertThat(test.getTmpObjectInstance(), nullValue());
		assertThat(test.getTmpIntList(), nullValue());
		assertThat(test.getTmpStringList(), nullValue());
	}
	
	private String createPar(final String option, final String value) {
		return asOptionName(option) + getOptionDelimiter() + value;
	}
}
