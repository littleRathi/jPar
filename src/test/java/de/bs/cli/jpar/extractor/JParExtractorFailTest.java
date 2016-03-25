package de.bs.cli.jpar.extractor;

import static org.junit.Assert.fail;

import org.junit.Test;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.examples.MissingCliProgramExample;
import de.bs.cli.jpar.examples.WrongFieldOptionExample;
import de.bs.cli.jpar.examples.WrongMethodOptionExample;

public class JParExtractorFailTest {
	@Test(expected=JParException.class)
	public void testMissingCliProgramExample() {
		new JParExtractor(MissingCliProgramExample.class);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testWrongFieldOptionExample() {
		new JParExtractor(WrongFieldOptionExample.class);
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testWrongMethodOptionExample() {
		new JParExtractor(WrongMethodOptionExample.class);
		
		fail();
	}
}
