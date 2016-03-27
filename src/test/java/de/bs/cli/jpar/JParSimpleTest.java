package de.bs.cli.jpar;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.config.Defaults;
import de.bs.cli.jpar.examples.MissingCliProgramExample;
import de.bs.cli.jpar.examples.WrongFieldOptionExample;
import de.bs.cli.jpar.examples.WrongMethodOptionExample;

public class JParSimpleTest {
	private static final String NEW_EMPTY = "";
	private static final String NEW_LIST_DELIMITER = "*";
	private static final String NEW_OPTION_DELIMITER = "=";
	private static final String NEW_OPTION_PREFIX = "--";
	
	private String saveDefaultListDelimiter;
	private String saveDefaultOptionDelimiter;
	private String saveDefaultOptionPrefix;
	
	@Before
	public void setupTest() {
		saveDefaultListDelimiter = Defaults.getListDelimiter();
		saveDefaultOptionDelimiter = Defaults.getOptionDelimiter();
		saveDefaultOptionPrefix = Defaults.getOptionPrefix();
	}
	
	@After
	public void teardownTest() {
		Defaults.setListDelimiter(saveDefaultListDelimiter);
		Defaults.setOptionDelimiter(saveDefaultOptionDelimiter);
		Defaults.setOptionPrefix(saveDefaultOptionPrefix);
	}
	
	@Test
	public void testSetDefaultListDelimiterCorrect() {
		JPar.setDefaultListDelimiter(NEW_LIST_DELIMITER);
		
		assertThat(Defaults.getListDelimiter(), equalTo(NEW_LIST_DELIMITER));
	}
	
	@Test(expected=JParException.class)
	public void testSetDefaultListDelimiterEmpty() {
		JPar.setDefaultListDelimiter(NEW_EMPTY);
		
		fail();
	}
	
	
	@Test
	public void testSetDefaultOptionDelimiterCorrect() {
		JPar.setDefaultOptionDelimiter(NEW_OPTION_DELIMITER);
		
		assertThat(Defaults.getOptionDelimiter(), equalTo(NEW_OPTION_DELIMITER));
	}
	
	@Test(expected=JParException.class)
	public void testSetDefaultOptionDelimiterEmpty() {
		JPar.setDefaultOptionDelimiter(NEW_EMPTY);
		
		fail();
	}
	
	
	@Test
	public void testSetDefaultOptionPrefixCorrect() {
		JPar.setDefaultOptionPrefix(NEW_OPTION_PREFIX);
		
		assertThat(Defaults.getOptionPrefix(), equalTo(NEW_OPTION_PREFIX));
	}
	
	@Test(expected=JParException.class)
	public void testSetDefaultOptionPrefixEmpty() {
		JPar.setDefaultOptionPrefix(NEW_EMPTY);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testProcessMissingCliProgramExample() {
		MissingCliProgramExample test = new MissingCliProgramExample();
		String[] args = new String[]{};
		
		JPar.process(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testProcessWrongFieldOptionExample() {
		WrongFieldOptionExample test = new WrongFieldOptionExample();
		String[] args = new String[]{};
		
		JPar.process(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testProcessWrongMethodOptionExample() {
		WrongMethodOptionExample test = new WrongMethodOptionExample();
		String[] args = new String[]{};
		
		JPar.process(test, args);
		
		fail();
	}
}
