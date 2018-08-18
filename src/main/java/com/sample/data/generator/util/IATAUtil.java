package com.sample.data.generator.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * Utility class to perform IATA related conversions
 * @author akuma921
 *
 */
public class IATAUtil {
	
	private static Map<String, String> iataCityMap = new HashMap<String, String>();
	private static Map<String, String> latLonIataMap = new HashMap<String, String>();
	
	/**
	 * Load the city mapping with lat/long from given 'iata' file
	 * @return Map<String, String>
	 * @throws IOException
	 */
	public static Map<String, String> loadCityMap() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(ConfigReader.getProperties().getProperty(Constants.IATA_FILE)));
		String line = "";
		while ((line = br.readLine()) != null) 
			iataCityMap.put(line.substring(line.indexOf("(")+1, line.indexOf(")")).trim(), line.replaceAll("\\(.*\\)", "").trim());
		
		br.close();
		return iataCityMap;
	}
	
	/**
	 * Return the city for given lat/long
	 * @param lat
	 * @param lon
	 * @return String
	 */
	public static String getCity(double lat, double lon) {
		//Skipping decimal values, as they leads to same IATA code. So no point of hitting the web service for similar geocodes
		String latLonKey = Double.toString(lat).replaceAll("\\..*", "").trim() + ":" + Double.toString(lon).replaceAll("\\..*", "").trim();
		//System.out.println(latLonKey);
		latLonIataMap.put(Constants.DEFAULT,Constants.DEFAULT_CITY);
		if(!latLonIataMap.containsKey(latLonKey)) {
			try {
				JSONObject jsonObject = RestClient.getInstance().get(ConfigReader.getProperties().getProperty(Constants.IATA_SERVICE)+lat+"/"+lon);
				if(jsonObject.get(Constants.IATA) != null && !jsonObject.get(Constants.IATA).toString().trim().equals("")) {
					if(iataCityMap.get(jsonObject.get(Constants.IATA).toString().trim()) != null && !iataCityMap.get(jsonObject.get(Constants.IATA).toString().trim()).equals("")) {
						latLonIataMap.put(latLonKey,iataCityMap.get(jsonObject.get(Constants.IATA).toString().trim()));
					} else {
						latLonIataMap.put(latLonKey,Constants.DEFAULT_CITY);
					}
				}
			} catch (Exception e) {
				return latLonIataMap.get(Constants.DEFAULT);
			}
		}
		return latLonIataMap.get(latLonKey);
	}
}
