package de.bs.cli.jpar.manual;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.containsString;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.config.Defaults;
import de.bs.cli.jpar.examples.MissingCliProgramExample;
import de.bs.cli.jpar.examples.WorkingFieldsExample;
import de.bs.cli.jpar.examples.WorkingMethodsExample;
import de.bs.cli.jpar.examples.WrongFieldOptionExample;
import de.bs.cli.jpar.examples.WrongMethodOptionExample;

public class JParManualTest {
	JParManual testee;
	
	@Before
	public void setupTest() {
		testee = new JParManual();
	}
	
	@After
	public void teardownTest() {
		testee = null;
	}
	
	@Test
	public void testHelpDescriptionForWorkingFieldsExample() {
		String result = testee.createHelpDescription(WorkingFieldsExample.class);
		
		assertThat(result, notNullValue());
		assertThat(result.length(), greaterThan(0));
		
		assertThat(result, containsString(JParManual.PART_PROG_NAME));
		assertThat(result, containsString(JParManual.PART_SYNOPSIS));
		assertThat(result, containsString(JParManual.PART_DESCRIPTION));
		assertThat(result, containsString(JParManual.PART_OPTIONS));
		
		assertThat(result, containsString(WorkingFieldsExample.PROG_NAME));
		assertThat(result, containsString(WorkingFieldsExample.PROG_DESC));
		
		assertThat(result, containsString(WorkingFieldsExample.OPT_BOOL_FIELD));
		assertThat(result, containsString(WorkingFieldsExample.OPT_CLASS_FIELD));
		assertThat(result, containsString(WorkingFieldsExample.OPT_LIST_INT_FIELD));
		assertThat(result, containsString(WorkingFieldsExample.OPT_LIST_STRING_FIELD));
		assertThat(result, containsString(WorkingFieldsExample.OPT_STRING_FIELD));
		
		assertThat(result, containsString(WorkingFieldsExample.STRING_VALUE_A));
		assertThat(result, containsString(WorkingFieldsExample.STRING_VALUE_B));
		assertThat(result, containsString(WorkingFieldsExample.STRING_VALUE_C));
		
		assertThat(result, containsString(WorkingFieldsExample.OPT_BOOL_DESC));
		assertThat(result, containsString(WorkingFieldsExample.OPT_CLASS_DESC));
		assertThat(result, containsString(WorkingFieldsExample.OPT_LIST_INT_DESC));
		assertThat(result, containsString(WorkingFieldsExample.OPT_LIST_STRING_DESC));
		assertThat(result, containsString(WorkingFieldsExample.OPT_STRING_DESC));
		
		String intListSimpleName = "<" + WorkingMethodsExample.OPT_LIST_INT_TYPE.getSimpleName() + ">";
		String intFormat = intListSimpleName + "[" + WorkingFieldsExample.LIST_INT_DELIMITER + intListSimpleName + "]";
		assertThat(result, containsString(intFormat));
		
		String stringListSimpleName = "<" + WorkingMethodsExample.OPT_LIST_STRING_TYPE.getSimpleName() + ">";
		String stringFormat = stringListSimpleName + "[" + Defaults.getListDelimiter() + stringListSimpleName + "]";
		assertThat(result, containsString(stringFormat));
	}
	
	@Test
	public void testHelpDescriptionForWorkingMethodsExample() {
		String result = testee.createHelpDescription(WorkingMethodsExample.class);
		
		assertThat(result, notNullValue());
		assertThat(result.length(), greaterThan(0));
		
		assertThat(result, containsString(JParManual.PART_PROG_NAME));
		assertThat(result, containsString(JParManual.PART_SYNOPSIS));
		assertThat(result, containsString(JParManual.PART_DESCRIPTION));
		assertThat(result, containsString(JParManual.PART_OPTIONS));
		
		assertThat(result, containsString(WorkingMethodsExample.PROG_NAME));
		assertThat(result, containsString(WorkingMethodsExample.PROG_DESC));
		
		assertThat(result, containsString(WorkingMethodsExample.OPT_BOOL_METHOD));
		assertThat(result, containsString(WorkingMethodsExample.OPT_CLASS_METHOD));
		assertThat(result, containsString(WorkingMethodsExample.OPT_LIST_INT_METHOD));
		assertThat(result, containsString(WorkingMethodsExample.OPT_LIST_STRING_METHOD));
		assertThat(result, containsString(WorkingMethodsExample.OPT_STRING_METHOD));
		
		assertThat(result, containsString(WorkingMethodsExample.STRING_LIST_VALUE_A));
		assertThat(result, containsString(WorkingMethodsExample.STRING_LIST_VALUE_B));
		assertThat(result, containsString(WorkingMethodsExample.STRING_LIST_VALUE_C));
		
		assertThat(result, containsString(WorkingMethodsExample.STRING_VALUE_A));
		assertThat(result, containsString(WorkingMethodsExample.STRING_VALUE_B));
		assertThat(result, containsString(WorkingMethodsExample.STRING_VALUE_C));
		
		assertThat(result, containsString(WorkingMethodsExample.OPT_BOOL_DESC));
		assertThat(result, containsString(WorkingMethodsExample.OPT_CLASS_DESC));
		assertThat(result, containsString(WorkingMethodsExample.OPT_LIST_INT_DESC));
		assertThat(result, containsString(WorkingMethodsExample.OPT_LIST_STRING_DESC));
		assertThat(result, containsString(WorkingMethodsExample.OPT_STRING_DESC));
		
		String intListSimpleName = "<" + WorkingMethodsExample.OPT_LIST_INT_TYPE.getSimpleName() + ">";
		String intFormat = intListSimpleName + "[" + Defaults.getListDelimiter() + intListSimpleName + "]";
		assertThat(result, containsString(intFormat));
		
		String stringListSimpleName = "<" + WorkingMethodsExample.OPT_LIST_STRING_TYPE.getSimpleName() + ">";
		String stringFormat = stringListSimpleName + "[" + Defaults.getListDelimiter() + stringListSimpleName + "]";
		assertThat(result, containsString(stringFormat));
	}
	
	@Test(expected=JParException.class)
	public void testHelpDescriptionForMissingCliProgramExample() {
		testee.createHelpDescription(MissingCliProgramExample.class);
	}
	
	@Test(expected=JParException.class)
	public void testHelpDescriptionForWrongFieldOptionExample() {
		testee.createHelpDescription(WrongFieldOptionExample.class);
	}
	
	@Test(expected=JParException.class)
	public void testHelpDescriptionForWrongMethodsOptionExample() {
		testee.createHelpDescription(WrongMethodOptionExample.class);
	}
}
