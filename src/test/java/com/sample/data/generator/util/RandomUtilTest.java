package com.sample.data.generator.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Test;

/**
 * RandomUtilTest
 * @author akuma921
 *
 */
public class RandomUtilTest {
	
	@Test
	public void testRandomTimeSuccess() {
		try {
			ConfigReader.readConfig("src/test/resources/config.properties");
			String[] timeRange = ConfigReader.getProperties().getProperty("TIME_RANGE").split(",");
			DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
			long minTime = formatter.parse(timeRange[0]).getTime();
			long maxTime = formatter.parse(timeRange[1]).getTime();
			long randomTime = formatter.parse(RandomUtil.randomTime()).getTime();
			Assert.assertTrue(minTime<=randomTime && randomTime<=maxTime);
		} catch (IOException | ParseException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testRandomTemperatureSuccess() {
		try {
			ConfigReader.readConfig("src/test/resources/config.properties");
			String[] rainConditions = ConfigReader.getProperties().getProperty(Constants.RAIN_CONDITION).split(",");
			String[] snowConditions = ConfigReader.getProperties().getProperty(Constants.SNOW_CONDITION).split(",");
			String[] sunnyConditions = ConfigReader.getProperties().getProperty(Constants.SUNNY_CONDITION).split(",");
			
			Assert.assertNotNull(rainConditions);
			Assert.assertNotNull(snowConditions);
			Assert.assertNotNull(sunnyConditions);
			
			Object[] climate = RandomUtil.randomTemperature();
			Assert.assertNotNull(climate);
			
			Assert.assertNotNull(climate[0]);
			Assert.assertNotNull(climate[1]);
			Assert.assertNotNull(climate[2]);
			Assert.assertNotNull(climate[3]);
			
			if(climate[0].toString().equals(Constants.RAIN)) {
				double randomTemp = (double) climate[1];
				double minTemp = Double.parseDouble(rainConditions[0]);
				double maxTemp = Double.parseDouble(rainConditions[1]);
				Assert.assertTrue(minTemp <= randomTemp && randomTemp <= maxTemp);
				
				double randomHumidity = (double) climate[2];
				double minHumidity = Double.parseDouble(rainConditions[2]);
				double maxHumidity = Double.parseDouble(rainConditions[3]);
				Assert.assertTrue(minHumidity <= randomHumidity && randomHumidity <= maxHumidity);
				
				double randomPressure = (double) climate[3];
				double minPressure = Double.parseDouble(rainConditions[4]);
				double maxPressure = Double.parseDouble(rainConditions[5]);
				Assert.assertTrue(minPressure <= randomPressure && randomPressure <= maxPressure);
				
			} else if(climate[0].toString().equals(Constants.SNOW)) {
				double randomTemp = (double) climate[1];
				double minTemp = Double.parseDouble(snowConditions[0]);
				double maxTemp = Double.parseDouble(snowConditions[1]);
				Assert.assertTrue(minTemp <= randomTemp && randomTemp <= maxTemp);
				
				double randomHumidity = (double) climate[2];
				double minHumidity = Double.parseDouble(snowConditions[2]);
				double maxHumidity = Double.parseDouble(snowConditions[3]);
				Assert.assertTrue(minHumidity <= randomHumidity && randomHumidity <= maxHumidity);
				
				double randomPressure = (double) climate[3];
				double minPressure = Double.parseDouble(snowConditions[4]);
				double maxPressure = Double.parseDouble(snowConditions[5]);
				Assert.assertTrue(minPressure <= randomPressure && randomPressure <= maxPressure);
				
			} else if(climate[0].toString().equals(Constants.SUNNY)) {
				double randomTemp = (double) climate[1];
				double minTemp = Double.parseDouble(sunnyConditions[0]);
				double maxTemp = Double.parseDouble(sunnyConditions[1]);
				Assert.assertTrue(minTemp <= randomTemp && randomTemp <= maxTemp);
				
				double randomHumidity = (double) climate[2];
				double minHumidity = Double.parseDouble(sunnyConditions[2]);
				double maxHumidity = Double.parseDouble(sunnyConditions[3]);
				Assert.assertTrue(minHumidity <= randomHumidity && randomHumidity <= maxHumidity);
				
				double randomPressure = (double) climate[3];
				double minPressure = Double.parseDouble(sunnyConditions[4]);
				double maxPressure = Double.parseDouble(sunnyConditions[5]);
				Assert.assertTrue(minPressure <= randomPressure && randomPressure <= maxPressure);
			}
			
		} catch (IOException e) {
			Assert.assertTrue(false);
		}
	}

}
