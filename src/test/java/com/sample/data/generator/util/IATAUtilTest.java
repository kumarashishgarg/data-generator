package com.sample.data.generator.util;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * IATAUtilTest
 * @author akuma921
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IATAUtilTest {
	
	@Test
	public void testBLoadCityMapSuccess() {
		try {
			ConfigReader.readConfig("src/test/resources/config.properties");
			Map<String, String> iataCityMap = IATAUtil.loadCityMap();
			Assert.assertNotNull(iataCityMap);
			Assert.assertTrue(iataCityMap.size() > 0);
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testCGetCitySuccess() {
		try {
			ConfigReader.readConfig("src/test/resources/config.properties");
			Map<String, String> iataCityMap = IATAUtil.loadCityMap();
			Assert.assertNotNull(iataCityMap);
			Assert.assertTrue(iataCityMap.size() > 0);
			String city = IATAUtil.getCity(66,-179);
			Assert.assertEquals(city, "Anadyr, Russia");
		} catch (NullPointerException | IOException e) {
			Assert.assertTrue(false);
		}
	}

}
