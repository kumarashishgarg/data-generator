package com.sample.data.generator.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * DataGeneratorExceptionTest
 * @author akuma921
 *
 */
public class DataGeneratorExceptionTest {
	
	@Test
	public void constructorTest() {
		String testMessage = "Test Message";
		DataGeneratorException dataGeneratorException = new DataGeneratorException(testMessage);
		Assert.assertNotNull(dataGeneratorException);
		Assert.assertEquals(dataGeneratorException.getMessage(), testMessage);
	}

}
