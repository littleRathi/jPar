package de.bs.cli.jpar.extractor.type;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.containsString;

import static de.bs.hamcrest.ClassMatchers.equalToType;
import static de.bs.hamcrest.ClassMatchers.extendsType;
import static de.bs.hamcrest.ClassMatchers.collection;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.config.Consts;
import de.bs.cli.jpar.config.Defaults;
import de.bs.cli.jpar.extractor.ExtractedOption;
import de.bs.cli.jpar.extractor.ExtractedArguments;
import de.bs.cli.jpar.process.Parameters;
import de.bs.cli.jpar.util.MockClassAnswer;

@SuppressWarnings("rawtypes")
public abstract class CollectionTypeTestBase<T extends Collection> {
	private CollectionType testee;
	private static final Class<?> SOURCE_TYPE = String.class;
	
	private ExtractedOption option;
	private ExtractedArguments arguments;
	
	private static final String EXTRACTED_ARGUMENT_ARG_NAME 
		= Defaults.getOptionPrefix() + "test";
	
	private static final String ARGUMENT_2A = "a00;a02";
	private static final String ARGUMENT_AB = "a00;b00";
	private static final String ARGUMENT_B = "b00";
	
	private static final String DELIMITER = ";";
	private static final String ALLOWED_VALUE_A0 = "a00";
	private static final String ALLOWED_VALUE_A1 = "a01";
	private static final String ALLOWED_VALUE_A2 = "a02";
	private static final String ALLOWED_VALUE_B0 = "b00";
	private static final String[][] ALLOWED_VALUES = new String[][]{
		{ALLOWED_VALUE_A0, ALLOWED_VALUE_A1, ALLOWED_VALUE_A2},{ALLOWED_VALUE_B0}
	};
	
	protected abstract Class<T> getCollectionType();
	protected abstract Collection<?> getCollectionInstance();
	protected abstract Collection<?> getWrongCollectionInstance();
		
	private ExtractedArguments mockExtractedArgumentsFull() {
		arguments = mock(ExtractedArguments.class);
		when(arguments.getDelimiter()).thenReturn(DELIMITER);
		when(arguments.getValues()).thenReturn(ALLOWED_VALUES);
		
		return arguments;
	}
	
	private ExtractedArguments mockExtractedArgumentsDelimiterOnly() {
		arguments = mock(ExtractedArguments.class);
		when(arguments.getDelimiter()).thenReturn(DELIMITER);
		
		return arguments;
	}
	
	private ExtractedArguments mockExtractedArgumentsEmpty() {
		arguments = mock(ExtractedArguments.class);
		when(arguments.getDelimiter()).thenReturn(Consts.EMPTY);
		return arguments;
	}
	
	private ExtractedOption mockExtractedOption() {
		option = mock(ExtractedOption.class);
		when(option.getOptionName()).thenReturn(EXTRACTED_ARGUMENT_ARG_NAME);
		when(option.getManualDescription()).thenReturn("Some meaningless text");
		when(option.getSourceType()).then(new MockClassAnswer(SOURCE_TYPE));
		
		return option;
	}
	
	@Before
	public void setupTest() {
		testee = new CollectionType(getCollectionType(), mockExtractedOption(), mockExtractedArgumentsFull());
	}

	
	// super ctor part
	@Test(expected=JParException.class)
	public void testSuperCtorMissingOption() {
		testee = new CollectionType(getCollectionType(), null, mockExtractedArgumentsFull());
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testSuperCtorMissingTargetType() {
		testee = new CollectionType(null, option, mockExtractedArgumentsFull());
		
		fail();
	}
	
	// CollectionType ctor part
	@Test
	public void testCtorSuccess() {
		option = mock(ExtractedOption.class);
		when(option.getManualDescription()).thenReturn("Something meaningless");
		when(option.getSourceType()).then(new MockClassAnswer(SOURCE_TYPE));
		
		testee = new CollectionType(getCollectionType(), option, mockExtractedArgumentsFull());
		
		assertThat(testee, notNullValue(CollectionType.class));
	}

	@Test(expected=JParException.class)
	public void testCtorProvokeWrongSourceType() {
		option = mock(ExtractedOption.class);
		when(option.getOptionName()).thenReturn(EXTRACTED_ARGUMENT_ARG_NAME);
		when(option.getSourceType()).thenReturn(null);
		
		testee = new CollectionType(getCollectionType(), option, mockExtractedArgumentsFull());
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testCtorProvokeArgumentsIsNull() {
		option = mock(ExtractedOption.class);
		when(option.getOptionName()).thenReturn(EXTRACTED_ARGUMENT_ARG_NAME);
		when(option.getManualDescription()).thenReturn("something meaningless as description");
		
		testee = new CollectionType(getCollectionType(), option, mockExtractedArgumentsFull());
		
		fail();
	}
	
	@Test
	public void testCtorEmptyArguments() {
		testee = new CollectionType(getCollectionType(), mockExtractedOption(), mockExtractedArgumentsEmpty());
		
		assertThat(testee, notNullValue());
	}
	
	@Test
	public void testCtorArgumentsOnlyDelimiter() {
		testee = new CollectionType(getCollectionType(), mockExtractedOption(), mockExtractedArgumentsDelimiterOnly());
		
		assertThat(testee, notNullValue());
	}
	
	@Test
	public void testCtorArgumentsFull() {
		testee = new CollectionType(getCollectionType(), mockExtractedOption(), mockExtractedArgumentsFull());
		
		assertThat(testee, notNullValue());
	}
	
	// type check test
	@Test
	public void testCollectionTestClass() {
		Class<T> collectionClass = getCollectionType();
		
		assertThat(collectionClass, extendsType(Collection.class));
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
		
		assertThat(returned, equalToType(getCollectionType()));
	}
	
	@Test
	public void testWithSourceType() {
		Class<?> returned = testee.getOption().getSourceType();
		
		assertThat(returned, equalToType(SOURCE_TYPE));
	}
	
	@Test(expected=JParException.class)
	public void testWithoutSourceType() {
		option = mockExtractedOption();
		when(option.getSourceType()).then(new MockClassAnswer(Void.class));
		
		testee = new CollectionType(getCollectionType(), option, null);
		
		fail();
	}
	
	// testcases:CollectionType
	@Test
	public void testGetShortDescription() {
		String result = testee.getShortDescription();
		
		String simpleName = SOURCE_TYPE.getSimpleName();
		String expected = "-test:<" + simpleName + ">[" 
				+ Defaults.getListDelimiter() + "<" + simpleName + ">]";
		assertThat(result, equalTo(expected));
	}
	
	@Test
	public void testGetManualDescription() {
		StringBuilder sb = new StringBuilder();
		testee.getManualDescription(sb);
		String description = sb.toString();
		
		assertThat(description, containsString(EXTRACTED_ARGUMENT_ARG_NAME));
		assertThat(description, containsString(SOURCE_TYPE.getSimpleName()));
		assertThat(description, containsString(DELIMITER));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testProcessArgs2AWithValues() {
		when(arguments.validValues((String[])isNotNull())).thenReturn(true);
		
		Parameters args = new Parameters(new String[]{EXTRACTED_ARGUMENT_ARG_NAME + ARGUMENT_2A});
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, ARGUMENT_2A, args);

		assertThat(result, collection(Collection.class, String.class)
				.and(hasItems(ALLOWED_VALUE_A0, ALLOWED_VALUE_A2)));
	}
	
	@Test(expected=JParException.class)
	public void testProcessArgsABWithValues() {
		when(arguments.validValues((String[])isNotNull())).thenReturn(false);
		
		Parameters args = new Parameters(new String[]{EXTRACTED_ARGUMENT_ARG_NAME + ARGUMENT_AB});
		testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, ARGUMENT_AB, args);
		
		fail();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testProcessArgsBWithValues() {
		when(arguments.validValues((String[])isNotNull())).thenReturn(true);
		
		Parameters args = new Parameters(new String[]{EXTRACTED_ARGUMENT_ARG_NAME + ARGUMENT_B});
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, ARGUMENT_B, args);
		
		assertThat(result, collection(Collection.class, String.class).and(hasItems(ALLOWED_VALUE_B0)));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testProcessArgs2AWithoutValuesProperty() {
		testee = new CollectionType(getCollectionType(), mockExtractedOption(), mockExtractedArgumentsDelimiterOnly());
		when(arguments.validValues((String[])isNotNull())).thenReturn(true);
		
		Parameters args = new Parameters(new String[]{EXTRACTED_ARGUMENT_ARG_NAME + ARGUMENT_2A});
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, ARGUMENT_2A, args);
		
		assertThat(result, collection(Collection.class, String.class).and(hasItems(ALLOWED_VALUE_A0, ALLOWED_VALUE_A2)));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testProcessArgsABWithoutValuesProperty() {
		testee = new CollectionType(getCollectionType(), mockExtractedOption(), mockExtractedArgumentsDelimiterOnly());
		when(arguments.validValues((String[])isNotNull())).thenReturn(true);
		
		Parameters args = new Parameters(new String[]{EXTRACTED_ARGUMENT_ARG_NAME + ARGUMENT_AB});
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, ARGUMENT_AB, args);
		
		assertThat(result, collection(Collection.class, String.class).and(hasItems(ALLOWED_VALUE_A0, ALLOWED_VALUE_B0)));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testProcessArgsBWithoutValuesProperty() {
		testee = new CollectionType(getCollectionType(), mockExtractedOption(), mockExtractedArgumentsDelimiterOnly());
		when(arguments.validValues((String[])isNotNull())).thenReturn(true);
		
		Parameters args = new Parameters(new String[]{EXTRACTED_ARGUMENT_ARG_NAME + ARGUMENT_B});
		Object result = testee.processArgs(EXTRACTED_ARGUMENT_ARG_NAME, ALLOWED_VALUE_B0, args);
		
		assertThat(result, collection(Collection.class, String.class).and(hasItems(ALLOWED_VALUE_B0)));
	}
}
