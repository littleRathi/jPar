package de.bs.cli.jpar.process;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import de.bs.cli.jpar.JParException;

public class ParametersTest {
	private static final String ARG_C = "c";
	private static final String ARG_B = "b";
	private static final String ARG_A = "a";
	private Parameters testee;
	
	@Test(expected=JParException.class)
	public void testCtorNull() {
		new Parameters(null);
		
		fail();
	}
	
	@Test
	public void testCtorEmptyArray() {
		testee = new Parameters(new String[0]);
		
		assertThat(testee.next(), equalTo(false));
	}
	
	@Test
	public void testCtorOneElement() {
		String[] test = new String[]{"abc"};
		testee = new Parameters(test);
		
		assertThat(testee.next(), equalTo(true));
	}
	
	@Test
	public void testNext() {
		String[] test = new String[]{ARG_A, ARG_B};
		testee = new Parameters(test);
		
		assertThat("first next must be true", testee.next(), equalTo(true));
		assertThat("second next must be true", testee.next(), equalTo(true));
		assertThat("third next must be false", testee.next(), equalTo(false));
	}
	
	@Test
	public void testGet() {
		String[] test = new String[]{ARG_A, ARG_B, ARG_C};
		testee = new Parameters(test);
		
		assertThat(testee.get(), equalTo(null));
		
		assertThat(testee.next(), equalTo(true));
		assertThat(testee.get(), equalTo(ARG_A));
		
		assertThat(testee.next(), equalTo(true));
		assertThat(testee.get(), equalTo(ARG_B));
		
		assertThat(testee.next(), equalTo(true));
		assertThat(testee.get(), equalTo(ARG_C));
		
		assertThat(testee.next(), equalTo(false));
		assertThat(testee.get(), equalTo(null));
	}
	
	@Test
	public void testBefore() {
		String[] test = new String[]{ARG_A, ARG_B, ARG_C};
		testee = new Parameters(test);
		
		assertThat(testee.next(), equalTo(true));
		assertThat(testee.next(), equalTo(true));
		assertThat(testee.get(), equalTo(ARG_B));
		assertThat(testee.before(), equalTo(true));
		assertThat(testee.get(), equalTo(ARG_A));
	}
	
	@Test
	public void testReset() {
		String[] test = new String[]{ARG_A, ARG_B, ARG_C};
		testee = new Parameters(test);
		
		assertThat(testee.next(), equalTo(true));
		assertThat(testee.next(), equalTo(true));
		assertThat(testee.next(), equalTo(true));
		assertThat(testee.get(), equalTo(ARG_C));
		testee.reset();
		assertThat(testee.get(), equalTo(ARG_A));
	}
}
