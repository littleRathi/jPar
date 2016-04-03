package de.bs.cli.jpar.process;

import static org.junit.Assert.fail;

import java.util.List;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasItems;
import static de.bs.hamcrest.ClassMatchers.fullQualifiedName;

import static de.bs.cli.jpar.config.Defaults.*;
import static de.bs.cli.jpar.extractor.ExtractedOption.asOptionName;

import static de.bs.cli.jpar.examples.WorkingFieldsExample.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.examples.WorkingFieldsExample;
import de.bs.cli.jpar.extractor.type.BooleanType;

public class JParProcessFieldsTest {
	private static final Integer INT_12 = 12;
	private static final Integer INT_24 = 24;
	
	private static final String boolValue = BooleanType.TRUE;
	private static final String classValue = "de.bs.cli.jpar.examples.WorkingFieldsExample";
	private static final String stringValue = "something";
	private static final String listIntValue = INT_12 + LIST_INT_DELIMITER + INT_24;
	private static final String listStringValue = STRING_VALUE_A + getListDelimiter() + STRING_VALUE_B;
	
	private WorkingFieldsExample test;
	private JParProcess testee;
	
	@Before
	public void setupTest() {
		testee = new JParProcess();
		test = new WorkingFieldsExample();
	}
	
	@After
	public void teardownTest() {
		testee = null;
		test = null;
	}
	
	@Test(expected=JParException.class)
	public void testNoArgs() {
		testee.processArgs(test, null);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testNoRequired() {
		String[] args = new String[]{
				createPar(OPT_BOOL_FIELD, boolValue)
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	@Test
	public void testOnlyRequiredOptions() {
		String[] args = new String[]{
			createPar(OPT_CLASS_FIELD, classValue),
			createPar(OPT_STRING_FIELD, stringValue)
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		assertThat(test.getClassField(), notNullValue());
		assertThat(test.getClassField().getClass(), fullQualifiedName(equalTo(classValue)));
		assertThat(test.getStringField(), equalTo(stringValue));
	}
	
	@Test
	public void testAllOptions() {
		String[] args = new String[]{
				createPar(OPT_BOOL_FIELD, boolValue),
				createPar(OPT_CLASS_FIELD, classValue),
				createPar(OPT_STRING_FIELD, stringValue),
				createPar(OPT_LIST_INT_FIELD, listIntValue),
				createPar(OPT_LIST_STRING_FIELD, listStringValue)
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		assertThat(test.isBoolField(), equalTo(true));
		
		assertThat(test.getClassField(), notNullValue());
		assertThat(test.getClassField().getClass(), fullQualifiedName(equalTo(classValue)));
		
		assertThat(test.getStringField(), equalTo(stringValue));
		
		List<Integer> intList = test.getListIntField();
		assertThat(intList, notNullValue());
		assertThat(intList, hasItems(INT_12, INT_24));
		
		List<String> stringList = test.getListStringField();
		assertThat(stringList, notNullValue());
		assertThat(stringList, hasItems(STRING_VALUE_A, STRING_VALUE_B));
	}
	
	@Test(expected=JParException.class)
	public void testBoolWrongWithRequiredOptions() {
		String[] args = new String[] {
				createPar(OPT_BOOL_FIELD, "abc"),
				createPar(OPT_CLASS_FIELD, classValue),
				createPar(OPT_STRING_FIELD, stringValue),
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testListIntWrongWithRequiredOptions() {
		String[] args = new String[] {
				createPar(OPT_LIST_INT_FIELD, "abc"),
				createPar(OPT_CLASS_FIELD, classValue),
				createPar(OPT_STRING_FIELD, stringValue),
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testListStringWrongWithRequiredOptions() {
		String[] args = new String[] {
				createPar(OPT_LIST_STRING_FIELD, "abcdefghijklmnopq"),
				createPar(OPT_CLASS_FIELD, classValue),
				createPar(OPT_STRING_FIELD, stringValue),
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testWrongClass() {
		String[] args = new String[]{
			createPar(OPT_CLASS_FIELD, "abcdefghijklmnopq"),
			createPar(OPT_STRING_FIELD, stringValue)
		};
		
		preValidation();
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	private void preValidation() {
		assertThat(test.isBoolField(), equalTo(false));
		assertThat(test.getClassField(), nullValue());
		assertThat(test.getStringField(), nullValue());
		assertThat(test.getListIntField(), nullValue());
		assertThat(test.getListStringField(), nullValue());
	}
	
	private String createPar(final String option, final String value) {
		return asOptionName(option) + getOptionDelimiter() + value;
	}
}
