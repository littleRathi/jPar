package de.bs.cli.jpar.extractor.type;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static de.bs.cli.jpar.config.Defaults.*;

import static de.bs.cli.jpar.extractor.type.BooleanType.FALSE;
import static de.bs.cli.jpar.extractor.type.BooleanType.TRUE;
import static de.bs.cli.jpar.extractor.type.BooleanType.TRUE_IMPLIZIT;

import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.extractor.ExtractedOption;
import de.bs.cli.jpar.extractor.ExtractedArguments;
import de.bs.cli.jpar.process.Parameters;

public class BooleanTypeTest {
	private BooleanType testee;
	
	private ExtractedOption option;
	
	private static final String EXTRACTED_ARGUMENT_ARG_NAME = getOptionPrefix() + "test";
	private static final String SHORT_DESCRIPTION = EXTRACTED_ARGUMENT_ARG_NAME + getOptionDelimiter() + "[+|-]";
	
	private static final String WRONG_VALUE = "abc";
	
	private ExtractedArguments mockExtractedValues() {
		ExtractedArguments values = mock(ExtractedArguments.class);
		when(values.getDelimiter()).thenReturn(";");
		
		return values;
	}
	
	private ExtractedOption mockExtractedOption() {
		option = mock(ExtractedOption.class);
		when(option.getOptionName()).thenReturn(EXTRACTED_ARGUMENT_ARG_NAME);
		when(option.getManualDescription()).thenReturn("Some meaningless text");
		return option;
	}
	
	@Before
	public void setupTest() {
		testee = new BooleanType(mockExtractedOption(), null);
	}
	
	// super ctor part
	@Test(expected=JParException.class)
	public void testSuperCtorMissingOption() {
		testee = new BooleanType(null, null);
		
		fail();
	}
	// ctor part
	@Test(expected=JParException.class)
	public void testCtorWithArguments() {
		testee = new BooleanType(mockExtractedOption(), mock(ExtractedArguments.class));
		
		fail();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(expected=JParException.class)
	public void testCtorSourceType() {
		when(option.getSourceType()).thenReturn((Class)String.class);
		
		testee = new BooleanType(option, null);
		
		fail();
	}
	
	// testcases:Type (superclass)
	@Test
	public void testGetOption() {
		ExtractedOption returned = testee.getOption();
		
		assertThat(returned, equalTo(option));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testGetTargetType() {
		Class returned = (Class)testee.getTargetType();
		
		assertThat(returned, equalTo((Class)Boolean.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(expected=JParException.class)
	public void testWithSourceType() {
		option = mockExtractedOption();
		when(option.getSourceType()).thenReturn((Class)String.class);
		
		testee = new BooleanType(option, null);
		
		fail();
	}
	
	// testcases:BooleanType
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
		assertThat(description, containsString(BooleanType.FALSE));
		assertThat(description, containsString(BooleanType.TRUE));
	}
	
	@Test
	public void testProcessArgsWithTrue() {
		String[] args = new String[]{EXTRACTED_ARGUMENT_ARG_NAME + TRUE};
		Parameters parameters = new Parameters(args);
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, TRUE, parameters);
		
		assertThat(result, equalTo((Object)Boolean.TRUE));
	}
	
	@Test
	public void testProcessArgsEmpty() {
		String[] args = new String[]{EXTRACTED_ARGUMENT_ARG_NAME + TRUE_IMPLIZIT};
		Parameters parameters = new Parameters(args);
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, TRUE_IMPLIZIT, parameters);
		
		assertThat(result, equalTo((Object)Boolean.TRUE));
	}
	
	@Test
	public void testProcessArgsWithFalse() {
		String[] args = new String[]{EXTRACTED_ARGUMENT_ARG_NAME + FALSE};
		Parameters parameters = new Parameters(args);
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, FALSE, parameters);
		
		assertThat(result, equalTo((Object)Boolean.FALSE));
	}
	
	@Test(expected=JParException.class)
	public void testProcessArgsWithAnyString() {
		String[] args = new String[]{EXTRACTED_ARGUMENT_ARG_NAME + WRONG_VALUE};
		Parameters parameters= new Parameters(args);
		testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, args[0], parameters);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testProcessArgsWithAnythingButArgValues() {
		testee = new BooleanType(mockExtractedOption(), mockExtractedValues());
		
		String[] args = new String[]{EXTRACTED_ARGUMENT_ARG_NAME + FALSE};
		Parameters parameters = new Parameters(args);
		testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, args[0], parameters);
		
		fail();
	}
}	
