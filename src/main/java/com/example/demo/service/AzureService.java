package com.example.demo.service;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.config.YAMLConfig;
import com.example.demo.model.WriteResult;

@Service
public class AzureService {
	
	private static final Logger LOG = getLogger(AzureService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private YAMLConfig yamlConfig;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public String getFieldsDetails(String url) throws Exception {
		LOG.info("getFieldsDetails() >> getting data from Azure...");

		try {
			final byte[] authBytes = yamlConfig.getPat().getBytes(UTF_8);

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(APPLICATION_JSON));
			headers.setBasicAuth(Base64.getEncoder().encodeToString(authBytes));
			HttpEntity<String> entity = new HttpEntity<String>(headers);

			return restTemplate.exchange(url, GET, entity, String.class).getBody();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}
	
	public String writeStatus() throws Exception {
		LOG.info("writeStatus() >> posting status to test case to Azure...");

		try {
			final byte[] authBytes = yamlConfig.getPat().getBytes(UTF_8);
			
			WriteResult writeResult = new WriteResult();
			writeResult.setId(100000);
			writeResult.setState("Completed");
			writeResult.setOutcome("Failed");
			
			List<WriteResult> writeResults = new ArrayList<WriteResult>();
			writeResults.add(writeResult);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(APPLICATION_JSON));
			headers.setBasicAuth(Base64.getEncoder().encodeToString(authBytes));
			HttpEntity<List<WriteResult>> entity = new HttpEntity<List<WriteResult>>(writeResults, headers);
			
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			restTemplate = new RestTemplate(requestFactory);
			// 
			return restTemplate.exchange("https://dev.azure.com/kellyservices/AIM/_apis/test/Runs/1004204/results?api-version=5.0-preview.5", PATCH, entity, String.class).getBody();
		} catch (Exception e) {
			LOG.error("--writeStatus() ", e.getMessage());
			throw new Exception(e.getCause());
		}

	}
	
	public String writeStatusByUrl(String url) throws Exception {
		LOG.info("writeStatus() >> posting status to test case to Azure...");

		try {
			final byte[] authBytes = yamlConfig.getPat().getBytes(UTF_8);
			
			WriteResult writeResult = new WriteResult();
			writeResult.setId(100000);
			writeResult.setState("Completed");
			writeResult.setOutcome("Failed");
			
			List<WriteResult> writeResults = new ArrayList<WriteResult>();
			writeResults.add(writeResult);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(APPLICATION_JSON));
			headers.setBasicAuth(Base64.getEncoder().encodeToString(authBytes));
			HttpEntity<List<WriteResult>> entity = new HttpEntity<List<WriteResult>>(writeResults, headers);
			
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			restTemplate = new RestTemplate(requestFactory);
			// 
			return restTemplate.exchange(url, PATCH, entity, String.class).getBody();
		} catch (Exception e) {
			LOG.error("--writeStatus() ", e.getMessage());
			throw new Exception(e.getCause());
		}

	}

}
