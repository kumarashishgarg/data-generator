package com.sample.data.generator.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * ConfigReaderTest
 * @author akuma921
 *
 */
public class ConfigReaderTest {
	
	@Test
	public void readConfigFailNoFile() {
		try {
			ConfigReader.readConfig("");
			Assert.assertNotNull(ConfigReader.getProperties());
			Assert.assertEquals(ConfigReader.getProperties().keySet().size(), 0);
			Assert.assertNull(ConfigReader.getProperties().get("INPUT_TOPOGRAPHY_FILE"));
		} catch (FileNotFoundException e) {
			Assert.assertTrue(true);
		} catch (IOException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void readConfigSuccess() {
		try {
			ConfigReader.readConfig("src/test/resources/config.properties");
			Assert.assertNotNull(ConfigReader.getProperties());
			Assert.assertTrue(ConfigReader.getProperties().keySet().size() > 0);
			Assert.assertNotNull(ConfigReader.getProperties().get("INPUT_TOPOGRAPHY_FILE"));
		} catch (FileNotFoundException e) {
			Assert.assertTrue(false);
		} catch (IOException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void getPropertiesSuccess() {
		try {
			ConfigReader.readConfig("src/test/resources/config.properties");
			Assert.assertNotNull(ConfigReader.getProperties());
			Assert.assertTrue(ConfigReader.getProperties().keySet().size() > 0);
			Assert.assertNotNull(ConfigReader.getProperties().get("WEATHER_CONDITIONS"));
			Assert.assertEquals(ConfigReader.getProperties().getProperty("WEATHER_CONDITIONS"), "Rain,Snow,Sunny");
		} catch (FileNotFoundException e) {
			Assert.assertTrue(false);
		} catch (IOException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void getPropertiesFail() {
		try {
			ConfigReader.readConfig("src/test/resources/config.properties");
			Assert.assertNotNull(ConfigReader.getProperties());
			Assert.assertTrue(ConfigReader.getProperties().keySet().size() > 0);
			Assert.assertNull(ConfigReader.getProperties().get("SOME_WRONG_KEY"));
		} catch (FileNotFoundException e) {
			Assert.assertTrue(false);
		} catch (IOException e) {
			Assert.assertTrue(false);
		}
	}
}
