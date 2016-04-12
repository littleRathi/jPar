package de.bs.cli.jpar.extractor.type;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.containsString;

import static de.bs.hamcrest.ClassMatchers.equalToType;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static de.bs.cli.jpar.config.Defaults.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.extractor.ExtractedArguments;
import de.bs.cli.jpar.extractor.ExtractedOption;
import de.bs.cli.jpar.process.Parameters;
import de.bs.cli.jpar.util.MockClassAnswer;

public class VoidTypeTest {
	private VoidType testee;
	
	private ExtractedOption option;
	
	private static final String EXTRACTED_ARGUMENT_ARG_NAME = getOptionPrefix() + "test";
	private static final String SHORT_DESCRIPTION = EXTRACTED_ARGUMENT_ARG_NAME;
	
	private ExtractedOption mockExtractedOption() {
		option = mock(ExtractedOption.class);
		when(option.getOptionName()).thenReturn(EXTRACTED_ARGUMENT_ARG_NAME);
		when(option.getManualDescription()).thenReturn("Einfache Beschreibung zu VoidType");
		
		return option;
	}
	
	private ExtractedArguments mockExtractedArguments() {
		ExtractedArguments arguments = mock(ExtractedArguments.class);
		
		return arguments;
	}
	
	@Before
	public void setupTest() {
		testee = new VoidType(mockExtractedOption(), null);
	}
	
	@After
	public void teardownTest() {
		testee = null;
	}
	
	// super ctor part
	@Test(expected=JParException.class)
	public void testSuperCtorMissingOption() {
		testee = new VoidType(null, null);
		
		fail();
	}

	// CollectionType ctor part
	@Test(expected=JParException.class)
	public void testCtorWrongSourceType() {
		when(option.getSourceType()).then(new MockClassAnswer(String.class));
		
		testee = new VoidType(option, null);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testCtorIllegalArguments() {
		testee = new VoidType(mockExtractedOption(), mock(ExtractedArguments.class));
		
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
		
		assertThat(returned, equalToType(Void.class));
	}
	
	@Test(expected=JParException.class)
	public void testWithSourceType() {
		option = mockExtractedOption();
		when(option.getSourceType()).then(new MockClassAnswer(String.class));
		
		testee = new VoidType(option, null);
		
		fail();
	}
	
	// testcases:VoidType
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
	}
	
	@Test
	public void testProcessArgsWith() {
		Parameters args = new Parameters(new String[]{EXTRACTED_ARGUMENT_ARG_NAME});
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, "", args);
		
		assertThat(result, nullValue());
	}
	
	@Test(expected=JParException.class)
	public void testConstructorWithExtractedArguments() {
		testee = new VoidType(mockExtractedOption(), mockExtractedArguments());
		
		fail();
	}
}
