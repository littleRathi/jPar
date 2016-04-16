package de.bs.cli.jpar;

import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ValuesTest {
	public static final String A = "A";
	public static final String B = "B";
	public static final String C = "C";
	public static class TEST_VALUES {
		public static final String A = ValuesTest.A;
		public static final String B = ValuesTest.B;
	}
	
	public static class ERROR_VALUES {
		public String D = "D";
	}
	
	@Test
	public void testCreateSimpleValueListClass() {
		String[] testee = Values.createSimpleValueList(TEST_VALUES.class, C);
		
		assertThat(testee, arrayContaining(TEST_VALUES.A, TEST_VALUES.B, C));
	}
	
	@Test
	public void testCreateSimpleValueListError() {
		String[] testee = Values.createSimpleValueList(ERROR_VALUES.class, C);
		
		assertThat(testee, arrayContaining(C));
	}
	
	@Test
	public void testCreateSimpleValueList() {
		String[] testee = Values.createSimpleValueList(A, B, C);
		
		assertThat(testee, arrayContaining(A, B, C));
	}
}
