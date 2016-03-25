package de.bs.cli.jpar.extractor;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import static de.bs.cli.jpar.extractor.ExtractedOption.asOptionName;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.config.Consts;
import de.bs.cli.jpar.examples.WorkingFieldsExample;

public class JParExtractorWorkingFieldsTest {
	private Class<?> testeeClass = WorkingFieldsExample.class;
	private JParExtractor testee;
	
	@Before
	public void setupTest() {
		testee = new JParExtractor(testeeClass);
	}
	
	@Test
	public void testNumberOfOptions() {
		List<ExtractedOption> result = testee.getExtractedOptions();
		
		assertThat(result, hasSize(6));
	}
	
	@Test
	public void testNumberOfRequiredOptions() {
		int result = testee.getRequiredExtractedOptions().size();
		
		assertThat(result, equalTo(2));
	}
	
	@Test
	public void testGetHelpOption() {
		ExtractedOption result = testee.getExtractedOptionForForOptionName(asOptionName(Consts.NAME_HELP));
		
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(Consts.NAME_HELP));
	}
	
	@Test
	public void testGetBoolFieldOption() {
		ExtractedOption result = testee.getExtractedOptionForForOptionName(asOptionName(WorkingFieldsExample.OPT_BOOL_FIELD));
		
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(WorkingFieldsExample.OPT_BOOL_FIELD));
	}
	
	@Test
	public void testGetClassFieldOption() {
		ExtractedOption result = testee.getExtractedOptionForForOptionName(asOptionName(WorkingFieldsExample.OPT_CLASS_FIELD));
		
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(WorkingFieldsExample.OPT_CLASS_FIELD));
	}
	
	@Test
	public void testGetStringFieldOption() {
		ExtractedOption result = testee.getExtractedOptionForForOptionName(asOptionName(WorkingFieldsExample.OPT_STRING_FIELD));
		
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(WorkingFieldsExample.OPT_STRING_FIELD));
	}
	
	@Test
	public void testGetListIntFieldOption() {
		ExtractedOption result = testee.getExtractedOptionForForOptionName(asOptionName(WorkingFieldsExample.OPT_LIST_INT_FIELD));
		
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(WorkingFieldsExample.OPT_LIST_INT_FIELD));
	}
	
	@Test
	public void testGetListStringFieldOption() {
		ExtractedOption result = testee.getExtractedOptionForForOptionName(asOptionName(WorkingFieldsExample.OPT_LIST_STRING_FIELD));
		
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(WorkingFieldsExample.OPT_LIST_STRING_FIELD));
	}
	
	@Test
	public void testGetProgram() {
		ExtractedProgram result = testee.getProgram();
		
		assertThat(result, notNullValue());
		assertThat(result.getProgramName(), equalTo(WorkingFieldsExample.PROG_NAME));
		assertThat(result.getDescription(), equalTo(WorkingFieldsExample.PROG_DESC));
	}
}
