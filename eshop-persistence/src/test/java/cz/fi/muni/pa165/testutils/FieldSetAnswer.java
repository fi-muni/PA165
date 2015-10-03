package cz.fi.muni.pa165.testutils;

import java.lang.reflect.Field;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Mockito Answer for tests. This answer will change field value of a specific argument of the tested method 
 * @author fnguyen
 *
 */
public class FieldSetAnswer implements Answer {
	private int argument;
	private String field;
	private Object value;
	
	public FieldSetAnswer(int arg, String field, Object value){
		this.argument = arg;
		this.field = field;
		this.value = value;
	}
	
	public Object answer(InvocationOnMock invocation)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException {
		Object arg =  invocation.getArguments()[argument];
		Field f = arg.getClass().getDeclaredField(field);
		f.setAccessible(true);
		f.set(arg, value);
		return null;
	}
}
