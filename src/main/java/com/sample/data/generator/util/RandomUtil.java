package com.sample.data.generator.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Utility class to perform random attribute generations
 * @author akuma921
 *
 */
public class RandomUtil {

	/**
	 * Return random time between the configured range
	 * @return String
	 * @throws ParseException
	 */
	public static String randomTime() throws ParseException {
		DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		String[] timeRange = ConfigReader.getProperties().getProperty(Constants.TIME_RANGE).split(",");
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatter.parse(timeRange[0]));
		Long value1 = cal.getTimeInMillis();
		cal.setTime(formatter.parse(timeRange[1]));
		Long value2 = cal.getTimeInMillis();
		long value3 = (long) (value1 + Math.random() * (value2 - value1));
		cal.setTimeInMillis(value3);
		return formatter.format(cal.getTime());
	}
	
	/**
	 * Return the climate in [Condition, Temperature, Humidity, Pressure] for configured range 
	 * @return Object[]
	 */
	public static Object[] randomTemperature() {
		String conditions[] = ConfigReader.getProperties().getProperty(Constants.WEATHER_CONDITIONS).split(",");
		String condition = conditions[(int)(0 + Math.random() * 3)];
		Object output[] = new Object[4]; 
		if(condition.equals(Constants.RAIN)) {
			output[0] = Constants.RAIN;
			String[] rainConditions = ConfigReader.getProperties().getProperty(Constants.RAIN_CONDITION).split(",");
			output[1] = (double) (Double.parseDouble(rainConditions[0]) + Math.random() * (Double.parseDouble(rainConditions[1]) - Double.parseDouble(rainConditions[0])));
			output[2] = (double) (Double.parseDouble(rainConditions[2]) + Math.random() * (Double.parseDouble(rainConditions[3]) - Double.parseDouble(rainConditions[2])));
			output[3] = (double) (Double.parseDouble(rainConditions[4]) + Math.random() * (Double.parseDouble(rainConditions[5]) - Double.parseDouble(rainConditions[4])));
		} else if(condition.equals(Constants.SNOW)) {
			String[] snowConditions = ConfigReader.getProperties().getProperty(Constants.SNOW_CONDITION).split(",");
			output[0] = Constants.SNOW;
			output[1] = (double) (Double.parseDouble(snowConditions[0]) + Math.random() * (Double.parseDouble(snowConditions[1]) - Double.parseDouble(snowConditions[0])));
			output[2] = (double) (Double.parseDouble(snowConditions[2]) + Math.random() * (Double.parseDouble(snowConditions[3]) - Double.parseDouble(snowConditions[2])));
			output[3] = (double) (Double.parseDouble(snowConditions[4]) + Math.random() * (Double.parseDouble(snowConditions[5]) - Double.parseDouble(snowConditions[4])));
		} else if(condition.equals(Constants.SUNNY)) {
			String[] sunnyConditions = ConfigReader.getProperties().getProperty(Constants.SUNNY_CONDITION).split(",");
			output[0] = Constants.SUNNY;
			output[1] = (double) (Double.parseDouble(sunnyConditions[0]) + Math.random() * (Double.parseDouble(sunnyConditions[1]) - Double.parseDouble(sunnyConditions[0])));
			output[2] = (double) (Double.parseDouble(sunnyConditions[2]) + Math.random() * (Double.parseDouble(sunnyConditions[3]) - Double.parseDouble(sunnyConditions[2])));
			output[3] = (double) (Double.parseDouble(sunnyConditions[4]) + Math.random() * (Double.parseDouble(sunnyConditions[5]) - Double.parseDouble(sunnyConditions[4])));
		} else {
			//TODO as per assumption it will never occur
		}
		return output;
	}
}
