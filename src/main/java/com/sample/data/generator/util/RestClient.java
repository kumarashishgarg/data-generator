package com.sample.data.generator.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sample.data.generator.exception.DataGeneratorException;

/**
 * Rest client to perform REST API query
 * @author akuma921
 *
 */
public class RestClient {
	
	private static RestClient restClient = null;
	private static final Log LOGGER = LogFactory.getLog(RestClient.class);
	
	/**
	 * private constructor
	 */
	private RestClient () {
		
	}
	
	/**
	 * Get Instance of RestClient
	 * @return RestClient
	 */
	public static RestClient getInstance() {
		if (restClient == null) 	restClient = new RestClient();
         return restClient;
	}
	
	/**
	 * Invoke GET for the input rest URL
	 * @param url
	 * @return JSONObject
	 * @throws DataGeneratorException
	 */
	public JSONObject get(String url) throws DataGeneratorException {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
			return jsonResponse.getBody().getObject();
		} catch (UnirestException e) {
			LOGGER.error("Exception while calling Rest API", e);
			throw new DataGeneratorException(e.getMessage());
		}
	}
	
}
