package de.bs.cli.jpar.extractor.type;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static de.bs.hamcrest.ClassMatchers.equalToType;
import static de.bs.hamcrest.ClassMatchers.ofType;
import static de.bs.cli.jpar.config.Defaults.getOptionDelimiter;
import static de.bs.cli.jpar.config.Defaults.getOptionPrefix;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.extractor.ExtractedArguments;
import de.bs.cli.jpar.extractor.ExtractedOption;
import de.bs.cli.jpar.process.Parameters;
import de.bs.cli.jpar.util.MockClassAnswer;

public class StringObjectTypeTest {
	private static final String EXTRACTED_ARGUMENT_ARG_NAME = getOptionPrefix() + "test";
	private static final String SHORT_DESCIPTION = EXTRACTED_ARGUMENT_ARG_NAME + getOptionDelimiter() + "<String>";
	
	private static final Class<?> TARGET_TYPE = String.class;
	
	private static final String VALID_VALUE_A00 = "a00";
	
	private StringObjectType testee;
	
	private ExtractedOption option;
	private ExtractedArguments arguments;
	
	private ExtractedOption mockExtractedOption() {
		option = mock(ExtractedOption.class);
		when(option.getOptionName()).thenReturn(EXTRACTED_ARGUMENT_ARG_NAME);
		when(option.getManualDescription()).thenReturn("description for the string type...");
		
		return option;
	}
	
	@Before
	public void setupTest() {
		testee = new StringObjectType(TARGET_TYPE, mockExtractedOption(), null);
	}
	
	@After
	public void teardownTest() {
		testee = null;
	}
	
	// ctor
	@Test
	public void testCtorWithString() {
		testee = new StringObjectType(String.class, mockExtractedOption(), null);
	}
	
	@Test
	public void testCtorWithFile() {
		testee = new StringObjectType(File.class, mockExtractedOption(), null);
	}
	
	@Test
	public void testCtorWithInteger() {
		testee = new StringObjectType(Integer.class, mockExtractedOption(), null);
	}
	
	@Test(expected=JParException.class)
	public void testCtorWithNull() {
		testee = new StringObjectType(null, mockExtractedOption(), null);
	}
	
	@Test(expected=JParException.class)
	public void testCtorWithStringObjectTypeTest() {
		testee = new StringObjectType(StringObjectTypeTest.class, mockExtractedOption(), null);
	}
	
	@Test(expected=JParException.class)
	public void testCtorWithNoExtractedOption() {
		testee = new StringObjectType(String.class, null, null);
	}
	
	@Test
	public void testCtorWithValuesIsNull() {
		arguments = mock(ExtractedArguments.class);
		when(arguments.getValues()).thenReturn(null);
		
		testee = new StringObjectType(String.class, mockExtractedOption(), arguments);
	}
	
	@Test
	public void testCtorWithValuesEmptyArray() {
		arguments = mock(ExtractedArguments.class);
		when(arguments.getValues()).thenReturn(new String[][]{{}});
		
		testee = new StringObjectType(String.class, mockExtractedOption(), arguments);
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
		
		assertThat(returned, equalTo(null));
	}
	
	@Test(expected=JParException.class)
	public void testGetSourceTypeIsString() {
		option = mockExtractedOption();
		when(option.getSourceType()).then(new MockClassAnswer(String.class));
		
		testee = new StringObjectType(TARGET_TYPE, option, null);
		
		Class<?> c = testee.getOption().getSourceType();
		
		System.out.println("c: " + c);
		
		fail();
	}
	
	
	// testcases:StringObjectType
	@Test
	public void testGetShortDescription() {
		String result = testee.getShortDescription();
		
		assertThat(result, equalTo(SHORT_DESCIPTION));
	}
	
	@Test
	public void testGetManualDescription() {
		StringBuilder sb = new StringBuilder();
		testee.getManualDescription(sb);
		String description = sb.toString();
		
		assertThat(description, containsString(EXTRACTED_ARGUMENT_ARG_NAME));
		assertThat(description, containsString("<String>"));
	}
	
	@Test
	public void testProcessingArgsWithA00() {
		Parameters args = new Parameters(new String[]{});
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, VALID_VALUE_A00, args);
		
		assertThat(result, instanceOf(String.class));
		assertThat(result, ofType(equalTo(String.class)).and(equalTo(VALID_VALUE_A00)));
	}

	@Test
	public void testProcessingArgsTypeInteger() {
		testee = new StringObjectType(Integer.class, mockExtractedOption(), null);
		
		Parameters args = new Parameters(new String[]{});
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, String.valueOf(123), args);
		
		assertThat(result, instanceOf(Integer.class));
		assertThat(result, ofType(equalTo(Integer.class)).and(equalTo(123)));
	}
	
	@Test
	public void testProcessingArgsTypeFile() {
		testee = new StringObjectType(File.class, mockExtractedOption(), null);
		
		Parameters args = new Parameters(new String[]{});
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, "./src/test/", args);
		
		assertThat(result, instanceOf(File.class));
		assertThat(result, ofType(equalTo(File.class)).and(equalTo(new File("./src/test/"))));
	}
}
