package de.bs.cli.jpar.util;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@SuppressWarnings("rawtypes")
public class MockClassAnswer implements Answer<Class> {
	private final Class<?> clazz;
	public MockClassAnswer(final Class<?> clazz) {
		this.clazz = clazz;
	}
	@Override
	public Class<?> answer(InvocationOnMock invocation) throws Throwable {
		return clazz;
	}

}
