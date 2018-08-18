package com.sample.data.generator.util;

import org.junit.Assert;
import org.junit.Test;

import com.sample.data.generator.exception.DataGeneratorException;

/**
 * RestClientTest
 * @author akuma921
 *
 */
public class RestClientTest {
	
	@Test
	public void testGetInstance() {
		RestClient restClient = RestClient.getInstance();
		Assert.assertNotNull(restClient);
	}
	
	@Test
	public void testGetSuccess() {
		RestClient restClient = RestClient.getInstance();
		Assert.assertNotNull(restClient);
		try {
			org.json.JSONObject jsonObject = restClient.get("http://iatageo.com/getCode/66/-177");
			Assert.assertNotNull(jsonObject);
			Assert.assertEquals(jsonObject.getString("IATA"), "PVS");
		} catch (DataGeneratorException e) {
			Assert.assertTrue(false);
		}
	}
}
