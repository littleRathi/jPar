package de.bs.cli.jpar.process;

import static org.junit.Assert.fail;

import java.util.List;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasItems;

import static de.bs.cli.jpar.config.Defaults.*;
import static de.bs.cli.jpar.extractor.ExtractedOption.asOptionName;

import static de.bs.cli.jpar.examples.WorkingMethodsExample.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.examples.WorkingMethodsExample;
import de.bs.cli.jpar.extractor.type.BooleanType;

public class JParProcessMethodsTest {
	private static final String boolValue = BooleanType.TRUE;
	private static final String classValue = "de.bs.cli.jpar.examples.WorkingMethodsExample";
	private static final String stringValue = STRING_VALUE_A;
	private static final String listIntValue = INT_LIST_VALUE_1 + getListDelimiter() + INT_LIST_VALUE_3 + getListDelimiter() + INT_LIST_VALUE_2;
	private static final String listStringValue = STRING_LIST_VALUE_A + getListDelimiter() + STRING_LIST_VALUE_B;
	
	private WorkingMethodsExample test = new WorkingMethodsExample();
	private JParProcess testee;
	
	@Before
	public void setupTest() {
		testee = new JParProcess();
	}
	
	@After
	public void teardownTest() {
		testee = null;
	}
	
	@Test(expected=JParException.class)
	public void testNoArgs() {
		testee.processArgs(test, null);
		
		fail();
	}
	@Test(expected=JParException.class)
	public void testNoRequired() {
		String[] args = new String[]{
				createPar(OPT_CLASS_METHOD, classValue)
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	@Test
	public void testOnlyRequiredOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_LIST_INT_METHOD, listIntValue)
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
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
		
		testee.processArgs(test, args);
		
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
	public void testClassWrongWithRequiredOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_CLASS_METHOD, "abcdefghijklmnopqrst"),
				createPar(OPT_LIST_INT_METHOD, listIntValue)
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testStringWrongWithRequiredOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_STRING_METHOD, "abcdefghijklmnopqrst"),
				createPar(OPT_LIST_INT_METHOD, listIntValue)
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testListStringWrongWithRequiredOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_LIST_INT_METHOD, listIntValue),
				createPar(OPT_LIST_STRING_METHOD, "abcdefghijklmnopqrst")
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testWrongBool() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, "abcdefghijklmnopqrstuvwxyz"),
				createPar(OPT_LIST_INT_METHOD, listIntValue)
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void estWrongListInt() {
		String[] args = new String[]{
				createPar(OPT_BOOL_METHOD, boolValue),
				createPar(OPT_LIST_INT_METHOD, "abcdefghijklmnopqrstuvwxyz")
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
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
