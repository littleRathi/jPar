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
import de.bs.cli.jpar.examples.WorkingMethodsExample;

public class JParExtractorWorkingMethodsTest {
	private Class<?> testeeClass = WorkingMethodsExample.class;
	private JParExtractor testee;
	
	@Before
	public void setuptTest() {
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
	public void testGetBoolMethodOption() {
		ExtractedOption result = testee.getExtractedOptionForForOptionName(asOptionName(WorkingMethodsExample.OPT_BOOL_METHOD));
		
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(WorkingMethodsExample.OPT_BOOL_METHOD));
	}
	
	@Test
	public void testGetClassMethodOption() {
		ExtractedOption result = testee.getExtractedOptionForForOptionName(asOptionName(WorkingMethodsExample.OPT_CLASS_METHOD));
		
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(WorkingMethodsExample.OPT_CLASS_METHOD));
	}
	
	@Test
	public void testGetStringMethodOption() {
		ExtractedOption result = testee.getExtractedOptionForForOptionName(asOptionName(WorkingMethodsExample.OPT_STRING_METHOD));
		
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(WorkingMethodsExample.OPT_STRING_METHOD));
	}
	
	@Test
	public void testGetListIntMethodOption() {
		ExtractedOption result = testee.getExtractedOptionForForOptionName(asOptionName(WorkingMethodsExample.OPT_LIST_INT_METHOD));
		
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(WorkingMethodsExample.OPT_LIST_INT_METHOD));
	}
	
	@Test
	public void testGetListStringMethodOption() {
		ExtractedOption result = testee.getExtractedOptionForForOptionName(asOptionName(WorkingMethodsExample.OPT_LIST_STRING_METHOD));
		
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(WorkingMethodsExample.OPT_LIST_STRING_METHOD));
	}
	
	@Test
	public void testGetProgram() {
		ExtractedProgram result = testee.getProgram();
		
		assertThat(result, notNullValue());
		assertThat(result.getProgramName(), equalTo(WorkingMethodsExample.PROG_NAME));
		assertThat(result.getDescription(), equalTo(WorkingMethodsExample.PROG_DESC));
	}
}
