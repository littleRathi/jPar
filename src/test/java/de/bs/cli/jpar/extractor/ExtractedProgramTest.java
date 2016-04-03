package de.bs.cli.jpar.extractor;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import static de.bs.hamcrest.ClassMatchers.equalToType;
import static de.bs.hamcrest.ArrayMatchers.arrayHasItems;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bs.cli.jpar.CliProgram;
import de.bs.cli.jpar.JParException;


public class ExtractedProgramTest {
	private ExtractedProgram testee;
	
	private static final String PROG_NAME = "testme";
	private static final String PROG_DESC = "some description for the program itself.";
	
	private static final String PROG_AUTHOR_A = "abc";
	private static final String PROG_AUTHOR_B = "def";
	private static final String PROG_AUTHOR_INVALID = "Mastermind";
	private static final String[] PROG_AUTHORS = new String[]{PROG_AUTHOR_A, PROG_AUTHOR_B};
	
	private static final String PROG_COPYRIGHT = "This program has some kind of copyright";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Before
	public void setupTest() {
		CliProgram programAnnotation = mock(CliProgram.class);
		when(programAnnotation.annotationType()).thenReturn((Class)CliProgram.class);
		when(programAnnotation.name()).thenReturn(PROG_NAME);
		when(programAnnotation.description()).thenReturn(PROG_DESC);
		when(programAnnotation.authors()).thenReturn(PROG_AUTHORS);
		when(programAnnotation.copyright()).thenReturn(PROG_COPYRIGHT);
		testee = new ExtractedProgram(getClass(), programAnnotation);
	}
	
	@After
	public void takeDownTest() {
		testee = null;
	}
	
	// ctors
	@Test(expected=JParException.class)
	public void testCtorMissingProgramClass() {
		testee = new ExtractedProgram(null, mock(CliProgram.class));
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testCtorMissingProgramAnnotation() {
		testee = new ExtractedProgram(getClass(), null);
		
		fail();
	}
	
	@Test
	public void testCtorCorrect() {
		testee = new ExtractedProgram(getClass(), mock(CliProgram.class));
		
		assertThat(testee, notNullValue());
	}
	
	// methods
	@Test
	public void testGetName() {
		String name = testee.getProgramName();
		
		assertThat(name, notNullValue());
		assertThat(name, equalTo(PROG_NAME));
	}
	
	@Test
	public void testGetType() {
		Class<?> type = testee.getType();
		
		assertThat(type, notNullValue());
		assertThat(type, equalToType(getClass()));
	}
	
	@Test
	public void testGetDescription() {
		String desc = testee.getDescription();
		
		assertThat(desc, notNullValue());
		assertThat(desc, equalTo(PROG_DESC));
	}
	
	@Test
	public void testGetAuthors() {
		String[] result = testee.getAuthors();
		
		assertThat(result, arrayHasItems(PROG_AUTHOR_A, PROG_AUTHOR_B));
	}
	
	@Test
	public void testGetAuthorsInvalidAuthor() {
		String[] result = testee.getAuthors();
		
		assertThat(result, not(arrayHasItems(PROG_AUTHOR_INVALID)));
	}
	
	@Test
	public void testGetCopyright() {
		String result = testee.getCopyright();
		
		assertThat(result, equalTo(PROG_COPYRIGHT));
	}
}
