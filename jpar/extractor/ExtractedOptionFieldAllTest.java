package de.bs.cli.jpar.extractor;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Collection;

import org.junit.Before;

import de.bs.cli.jpar.Option;
import de.bs.cli.jpar.config.Consts;
import de.bs.cli.jpar.config.Defaults;

public class ExtractedOptionFieldAllTest extends ExtractedOptionBaseTest {
	private static final Class<?> OPT_SOURCE_TYPE = String.class;
	public static final String OPT_NAME = "all";
	public static final String OPT_DESCRIPTION = "example description bla bla bla";
	public static final boolean OPT_REQUIRED = !Consts.REQUIRED;
	public static final String OPT_FIELD_NAME = "testFieldAll";
	public static final String[][] ARG_VALID = {{"a", "b"}};
	public static final String ARG_SUCCESS = "a" + Defaults.getListDelimiter() + "b";
	public static final String ARG_FAIL = "b" + Defaults.getListDelimiter() + "c";
	public Field optField;
	
	@Option(name=OPT_NAME, description = OPT_DESCRIPTION, required=OPT_REQUIRED, sourceType=String.class)
	private Collection<String> testFieldAll;
	
	private Field getField() throws NoSuchFieldException, SecurityException {
		return getClass().getDeclaredField(OPT_FIELD_NAME);
	}
	
	private Option getOptionAnnotation(final Field field) {
		return field.getAnnotation(Option.class);
	}
	
	private ExtractedArguments mockArguments() {
		ExtractedArguments args = mock(ExtractedArguments.class);
		when(args.getDelimiter()).thenReturn(Defaults.getListDelimiter());
		when(args.getValues()).thenReturn(ARG_VALID);
		when(args.validValues(ARG_VALID[0])).thenReturn(true);
		return args;
	}
	
	@Before
	public void setupTest() throws NoSuchFieldException, SecurityException {
		optField = getField();
		optField.setAccessible(true);
		this.option = getOptionAnnotation(optField);
		this.testee = new ExtractedOptionField(optField, option, mockArguments());
	}

	@Override
	protected String getName() {
		return OPT_NAME;
	}

	@Override
	protected Class<?> getTargetType() {
		return optField.getType();
	}

	@Override
	protected String getTargetName() {
		return OPT_FIELD_NAME;
	}

	@Override
	protected Class<?> getSourceType() {
		return OPT_SOURCE_TYPE;
	}

	@Override
	protected String getManualDecription() {
		return OPT_DESCRIPTION;
	}

	@Override
	protected boolean getRequired() {
		return OPT_REQUIRED;
	}
	
	@Override
	protected String successArgument() {
		return ARG_SUCCESS;
	}
	
	@Override
	protected void successCheck() {
		assertThat(testFieldAll, notNullValue());
		assertThat(testFieldAll, hasSize(2));
		assertThat(testFieldAll, hasItems("a", "b"));
	}
	
	@Override
	protected String failArgument()  {
		return ARG_FAIL;
	}
}
