package com.sample.data.generator.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Reader for the data generator application
 * @author akuma921
 *
 */
public class ConfigReader {
	
	private static Properties configProperties = new Properties(); 
	
	/**
	 * Read configurations from the given file
	 * @param fileName
	 * @return Properties
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void readConfig(String fileName) throws FileNotFoundException, IOException {
		configProperties.load(new FileInputStream(fileName));
	}
	
	public static Properties getProperties() {
		return configProperties;
	}
}
