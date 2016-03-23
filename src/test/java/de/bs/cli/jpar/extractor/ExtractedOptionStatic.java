package de.bs.cli.jpar.extractor;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.bs.cli.jpar.config.Defaults;

public class ExtractedOptionStatic {
	private static String saveOptionPrefix;
	
	@BeforeClass
	public static void setupTestClass() {
		saveOptionPrefix = Defaults.getOptionPrefix();
	}
	
	@AfterClass
	public static void setdownTestClass() {
		Defaults.setOptionPrefix(saveOptionPrefix);
	}
	
	@Test
	public void testAsOptionNameDash() {
		String prefix = "-";
		String name = "abc";
		Defaults.setOptionPrefix(prefix);
		
		String testee = ExtractedOption.asOptionName(name);
		
		assertThat(testee, equalTo(prefix + name));
	}
	
	@Test
	public void testAsOptionNameDollar() {
		String prefix = "$";
		String name = "test";
		Defaults.setOptionPrefix(prefix);
		
		String testee = ExtractedOption.asOptionName(name);
		
		assertThat(testee, equalTo(prefix + name));
	}
	
	@Test
	public void testAsOptionNameSlash() {
		String prefix = "/";
		String name = "fpfo";
		Defaults.setOptionPrefix(prefix);
		
		String testee = ExtractedOption.asOptionName(name);
		
		assertThat(testee, equalTo(prefix + name));
	}
	
	@Test
	public void testAsElName() {
		String name = "booringName";
		
		String testee = ExtractedOption.asElName(name);
		
		assertThat(testee, equalTo(name.toUpperCase()));
	}
}
