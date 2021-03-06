package de.bs.cli.jpar.extractor.type;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.StringContains.containsString;

import static de.bs.hamcrest.ClassMatchers.equalToType;

import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static de.bs.cli.jpar.config.Defaults.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.extractor.ExtractedOption;
import de.bs.cli.jpar.extractor.ExtractedArguments;
import de.bs.cli.jpar.process.Parameters;
import de.bs.cli.jpar.util.MockClassAnswer;

public class ClassObjectTypeTest {
	private ClassObjectType testee;
	
	// use list because of the existing subclasses
	private static final Class<?> TARGET_TYPE = List.class;
	private static final Class<?> SOURCE_TYPE = Class.class;
	
	private ExtractedOption option;
	private ExtractedArguments arguments;

	private static final String EXTRACTED_ARGUMENT_ARG_NAME = getOptionPrefix() + "test";
	private static final String SHORT_DESCRIPTION = EXTRACTED_ARGUMENT_ARG_NAME + getOptionDelimiter() + "<" + TARGET_TYPE.getName() + ">";
	
	@SuppressWarnings("rawtypes")
	private static final LinkedList VALID_LINKED_LIST = new LinkedList();
	@SuppressWarnings("rawtypes")
	private static final ArrayList VALID_ARRAY_LIST = new ArrayList();
	private static final String WRONG_VALUE = "abc";
	
	private static final String[][] ALLOWED_VALUES = new String[][]{
		{VALID_LINKED_LIST.getClass().getName()}
	};
	
	private ExtractedOption mockExtractedOption() {
		option = mock(ExtractedOption.class);
		when(option.getOptionName()).thenReturn(EXTRACTED_ARGUMENT_ARG_NAME);
		when(option.getManualDescription()).thenReturn("some description for ClassObjectType");
		when(option.getSourceType()).then(new MockClassAnswer(SOURCE_TYPE));
		return option;
	}
	
	private ExtractedArguments mockExtractedArguments() {
		arguments = mock(ExtractedArguments.class);
		when(arguments.getValues()).thenReturn(ALLOWED_VALUES);
		
		return arguments;
	}
	
	@Before
	public void setupTest() {
		testee = new ClassObjectType(TARGET_TYPE, mockExtractedOption(), null);
	}
	
	@After
	public void teardownTest() {
		testee = null;
	}
	
	// super ctor part
	@Test(expected=JParException.class)
	public void testSuperCtorMissingOption() {
		testee = new ClassObjectType(TARGET_TYPE, null, null);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testSuperCtorMissingTargetType() {
		testee = new ClassObjectType(null, mockExtractedOption(), null);
		
		fail();
	}
	
	// ctor part
	@Test(expected=JParException.class)
	public void testCtorWrongSourceType() {
		when(option.getSourceType()).then(new MockClassAnswer(String.class));
		
		testee = new ClassObjectType(TARGET_TYPE, option, null);
		
		fail();
	}
	
	// testcases:Type (superclass)
	@Test
	public void testGetOption() {
		ExtractedOption returned = testee.getOption();
		
		assertThat(returned, equalTo(option));
	}
	
	@Test
	public void testGetTargetType() {
		Class<?> returned = testee.getTargetType();
		
		assertThat(returned, equalToType(TARGET_TYPE));
	}
	
	@Test
	public void testGetSourceType() {
		Class<?> returned = testee.getOption().getSourceType();
		
		assertThat(returned, equalToType(SOURCE_TYPE));
	}
	
	@Test(expected=JParException.class)
	public void testGetSourceTypeWrong() {
		option = mockExtractedOption();
		when(option.getSourceType()).then(new MockClassAnswer(String.class));
		testee = new ClassObjectType(TARGET_TYPE, option, null);
		
		testee.getOption().getSourceType();
		
		fail();
	}
	
	// testcases:ClassObjectType
	@Test
	public void testGetShortDescription() {
		String result = testee.getShortDescription();
		
		assertThat(result, equalTo(SHORT_DESCRIPTION));
	}
	
	@Test
	public void testGetManualDescription() {
		StringBuilder sb = new StringBuilder();
		testee.getManualDescription(sb);
		String description = sb.toString();
		
		assertThat(description, containsString(EXTRACTED_ARGUMENT_ARG_NAME));
		assertThat(description, containsString("<class>"));
		assertThat(description, containsString("Interface"));
		assertThat(description, containsString(TARGET_TYPE.getName()));
	}
	
	@Test
	public void testProcessArgsLinkedList() {
		String[] args = new String[]{EXTRACTED_ARGUMENT_ARG_NAME + VALID_LINKED_LIST.getClass().getName()};
		Parameters parameters = new Parameters(args);
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, VALID_LINKED_LIST.getClass().getName(), parameters);
		
		assertThat(result, instanceOf(VALID_LINKED_LIST.getClass()));
	}
	
	@Test
	public void testProcessArgsArrayList() {
		String[] args = new String[]{EXTRACTED_ARGUMENT_ARG_NAME + VALID_ARRAY_LIST.getClass().getName()};
		Parameters parameters = new Parameters(args);
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, VALID_ARRAY_LIST.getClass().getName(), parameters);
		
		assertThat(result, instanceOf(VALID_ARRAY_LIST.getClass()));
	}
	
	@Test(expected=JParException.class)
	public void testProcessArgsString() {
		String[] args = new String[]{EXTRACTED_ARGUMENT_ARG_NAME + WRONG_VALUE};
		Parameters parameters = new Parameters(args);
		testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, WRONG_VALUE, parameters);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testProcessArgsList() {
		String[] args = new String[]{EXTRACTED_ARGUMENT_ARG_NAME + List.class.getName()};
		Parameters parameters = new Parameters(args);
		testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, List.class.getName(), parameters);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testProcessArgsArrayListWithValuesProperty() {
		testee = new ClassObjectType(TARGET_TYPE, mockExtractedOption(), mockExtractedArguments());
		when(arguments.validValue((String)isNotNull())).thenReturn(false);
		
		String[] args = new String[]{EXTRACTED_ARGUMENT_ARG_NAME + VALID_ARRAY_LIST.getClass().getName()};
		Parameters parameters = new Parameters(args);
		testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, VALID_ARRAY_LIST.getClass().getName(), parameters);
		
		fail();
	}
	
	@Test
	public void testProcessArgsLinkedListWithValuesProperty() {
		testee = new ClassObjectType(TARGET_TYPE, mockExtractedOption(), mockExtractedArguments());
		when(arguments.validValue((String)isNotNull())).thenReturn(true);
		
		String[] args = new String[]{EXTRACTED_ARGUMENT_ARG_NAME + VALID_LINKED_LIST.getClass().getName()};
		Parameters parameters = new Parameters(args);
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, VALID_LINKED_LIST.getClass().getName(), parameters);

		assertThat(result, instanceOf(VALID_LINKED_LIST.getClass()));
	}
}
