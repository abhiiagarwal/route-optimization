package com.example.route.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("LogisticOSRouteOptimization")
public class LogisticOSServiceImpl implements IRouteoptimizationService {

	private org.slf4j.Logger log;
	@Autowired
	@Qualifier("sslRestTemplate")
	private RestTemplate restTemplate;
	@Override
	public String submitJob(Object routeOptimizationRequest) throws Exception {

		String jobId = "";
		String optimizedRoute = "";
		ObjectMapper mapperObj = new ObjectMapper();
		JSONObject object;
		long start = System.currentTimeMillis();

		try {
			String jsonInString = mapperObj.writeValueAsString(routeOptimizationRequest);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("x-api-key", "n0m8uXtg7PWTnd2bdskw49TPhSZe8Ev11ZTOjkR1");
			HttpEntity<String> entity = new HttpEntity<>(jsonInString, headers);
			jobId = restTemplate.postForObject("https://api.logisticsos.com/v1/vrp/async", entity, String.class);
			object = new JSONObject(jobId);
			String obj = (String) object.get("job_id");
			log.info("Final Response for Routes, Logistic OS-->" + optimizedRoute);
			object = new JSONObject(optimizedRoute);
			JSONArray array = object.getJSONArray("routes");
		} catch (JsonProcessingException e) {
			log.error("Posting JSON Error: " + e.getMessage());
			e.printStackTrace();
		}
		return jobId;
	}


}
