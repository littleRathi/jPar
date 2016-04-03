package de.bs.cli.jpar.extractor.type;

import static de.bs.hamcrest.ClassMatchers.equalToType;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.extractor.ExtractedArguments;
import de.bs.cli.jpar.extractor.ExtractedOption;

public class CollectionTypeTestForType {
	private CollectionType testee;
	
	@After
	public void teardownTest() {
		testee = null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ExtractedOption mockExtractedOption(final Class sourceType) {
		ExtractedOption option = mock(ExtractedOption.class);
		when(option.getSourceType()).thenReturn(sourceType);
		return option;
	}
	
	private ExtractedArguments mockExtractedArguments() {
		ExtractedArguments arguments = mock(ExtractedArguments.class);
		when(arguments.getDelimiter()).thenReturn(";");
		return arguments;
	}
	
	// check target type
	@Test(expected=JParException.class)
	public void testTargetTypeVoid() {
		testee = new CollectionType(Void.class, mockExtractedOption(String.class), mockExtractedArguments());
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testTargetTypevoid() {
		testee = new CollectionType(void.class, mockExtractedOption(String.class), mockExtractedArguments());
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testTargetTypeObject() {
		testee = new CollectionType(Object.class, mockExtractedOption(String.class), mockExtractedArguments());
		
		fail();
	}
	
	@Test
	public void testTargetTypeHashSet() {
		testee = new CollectionType(HashSet.class, mockExtractedOption(String.class), mockExtractedArguments());
		
		assertThat(testee.getTargetType(), equalToType(HashSet.class));
	}
	
	@Test
	public void testTargetTypeSet() {
		testee = new CollectionType(Set.class, mockExtractedOption(String.class), mockExtractedArguments());
		
		assertThat(testee.getTargetType(), equalToType(Set.class));
	}
	
	@Test
	public void testTargetTypeArrayList() {
		testee = new CollectionType(ArrayList.class, mockExtractedOption(String.class), mockExtractedArguments());
		
		assertThat(testee.getTargetType(), equalToType(ArrayList.class));
	}
	
	@Test
	public void testTargetTypeLinkedList() {
		testee = new CollectionType(LinkedList.class, mockExtractedOption(String.class), mockExtractedArguments());
		
		assertThat(testee.getTargetType(), equalToType(LinkedList.class));
	}
	
	@Test
	public void testTargetTypeList() {
		testee = new CollectionType(List.class, mockExtractedOption(String.class), mockExtractedArguments());
		
		assertThat(testee.getTargetType(), equalToType(List.class));
	}
	
	@Test
	public void testTargetTypePriorityQueue() {
		testee = new CollectionType(PriorityQueue.class, mockExtractedOption(String.class), mockExtractedArguments());
		
		assertThat(testee.getTargetType(), equalToType(PriorityQueue.class));
	}
	
	@Test
	public void testTargetTypeCollection() {
		testee = new CollectionType(Collection.class, mockExtractedOption(String.class), mockExtractedArguments());
		
		assertThat(testee.getTargetType(), equalToType(Collection.class));
	}
	
	// check source type
	@Test
	public void testSourceTypeString() {
		testee = new CollectionType(Set.class, mockExtractedOption(String.class), mockExtractedArguments());
		
		assertThat(testee.getOption().getSourceType(), equalToType(String.class));
	}
	
	@Test
	public void testSourceTypeFile() {
		testee = new CollectionType(Set.class, mockExtractedOption(File.class), mockExtractedArguments());
		
		assertThat(testee.getOption().getSourceType(), equalToType(File.class));
	}
	
	@Test
	public void testSourceTypeInteger() {
		testee = new CollectionType(Set.class, mockExtractedOption(Integer.class), mockExtractedArguments());
		
		assertThat(testee.getOption().getSourceType(), equalToType(Integer.class));
	}
	
	@Test(expected=JParException.class)
	public void testSourceTypeObject() {
		testee = new CollectionType(Set.class, mockExtractedOption(Object.class), mockExtractedArguments());
		
		fail();
	}
	
	@Test(expected=JParException.class)
	public void testSourceTypeList() {
		testee = new CollectionType(Set.class, mockExtractedOption(List.class), mockExtractedArguments());
		
		fail();
	}
}
