package de.bs.cli.jpar.process;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.examples.MissingCliProgramExample;
import de.bs.cli.jpar.examples.WorkingFieldsExample;
import de.bs.cli.jpar.examples.WrongFieldOptionExample;
import de.bs.cli.jpar.examples.WrongMethodOptionExample;

public class JParProcessFailTest {
	private JParProcess testee;
	
	@Before
	public void setupTest() {
		testee = new JParProcess();
	}
	
	@After
	public void teardownTest() {
		testee = null;
	}
	
	@Test(expected=JParException.class)
	public void testWithNoArgs() {
		WorkingFieldsExample test = new WorkingFieldsExample();
		
		testee.processArgs(test, null);
		
		fail();
	}
	
	@Test(expected=NullPointerException.class)
	public void testWithNoProgram() {
		String[] args = new String[]{"test"};
		
		testee.processArgs(null, args);
		
		fail();
	}
	
	@Test(expected=NullPointerException.class)
	public void testAllNull() {
		testee.processArgs(null, null);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testMissingCliProgramExample() {
		MissingCliProgramExample test = new MissingCliProgramExample();
		String[] args = new String[]{"nothing", "relevant"};
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testWrongFieldOptionExample() {
		WrongFieldOptionExample test = new WrongFieldOptionExample();
		String[] args = new String[]{"error"};
		
		testee.processArgs(test, args);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testWrongMethodOptionExample() {
		WrongMethodOptionExample test = new WrongMethodOptionExample();
		String[] args = new String[]{"test"};
		
		testee.processArgs(test, args);
		
		fail();
	}
}
