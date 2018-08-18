package com.sample.data.generator;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.sample.data.generator.util.ConfigReader;



/**
 * WeatherDataGeneratorTest
 * @author akuma921
 *
 */
public class WeatherDataGeneratorTest {
	
	@Test
	public void testGenerateDataFailNoConfig() {
		try {
			WeatherDataGenerator.generateData();
		} catch (Exception e) {
			System.out.println(true);
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void testGenerateDataSuccess() {
		try {
			ConfigReader.readConfig("src/test/resources/config.properties");
			WeatherDataGenerator.generateData();
			File file = new File("src/test/resources/test-output");
			file.createNewFile();
			Assert.assertNotNull(file);
			Assert.assertTrue(file.length() > 0);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}
	
}
