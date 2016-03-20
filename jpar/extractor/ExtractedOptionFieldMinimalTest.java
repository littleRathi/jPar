package de.bs.cli.jpar.extractor;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;

import java.lang.reflect.Field;

import org.junit.Before;

import de.bs.cli.jpar.Option;
import de.bs.cli.jpar.config.Consts;
import de.bs.cli.jpar.extractor.type.BooleanType;

public class ExtractedOptionFieldMinimalTest extends ExtractedOptionBaseTest {
	public static final String OPT_NAME = "all";
	public static final String OPT_DESCRIPTION = "example description test abc";
	public static final String OPT_FIELD_NAME = "testFieldMinimal";
	public static final String ARG_FAIL = "fail";
	public Field optField;
	
	@Option(name=OPT_NAME, description=OPT_DESCRIPTION)
	private boolean testFieldMinimal;

	private Field getField() throws NoSuchFieldException, SecurityException {
		return getClass().getDeclaredField(OPT_FIELD_NAME);
	}
	
	private Option getOptionAnnotation(final Field field) {
		return field.getAnnotation(Option.class);
	}
	
	@Before
	public void setupTest() throws NoSuchFieldException, SecurityException {
		optField = getField();
		optField.setAccessible(true);
		this.option = getOptionAnnotation(optField);
		this.testee = new ExtractedOptionField(optField, option, null);
	}
	
	@Override
	protected String getName() {
		return OPT_NAME;
	}

	@Override
	protected Class<?> getTargetType() {
		return Boolean.class;
	}

	@Override
	protected String getTargetName() {
		return OPT_FIELD_NAME;
	}

	@Override
	protected Class<?> getSourceType() {
		return Consts.SOURCE_TYPE;
	}

	@Override
	protected String getManualDecription() {
		return OPT_DESCRIPTION;
	}

	@Override
	protected boolean getRequired() {
		return Consts.REQUIRED;
	}
	
	@Override
	protected String successArgument() {
		return BooleanType.TRUE;
	}
	
	@Override
	protected void successCheck() {
		assertThat(testFieldMinimal, equalTo(Boolean.TRUE));
	}
	
	@Override
	protected String failArgument()  {
		return ARG_FAIL;
	}

}
