package com.example.demo.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UrlMapping;
import com.example.demo.service.AzureService;

@RestController
public class DemoController {
	
	@Autowired
	private AzureService azureService;
	
	@GetMapping("/hello")
	public ResponseEntity<Object> getHello(){
		return new ResponseEntity<Object>("Hello There", OK);
	}
	
	@GetMapping("/writeStatus")
	public ResponseEntity<Object> writeStatus() throws Exception{
		return new ResponseEntity<Object>(azureService.writeStatus(), OK);
	}
	
	@PostMapping("/writeStatusByUrl")
	public ResponseEntity<Object> writeStatusByUrl(@RequestBody UrlMapping urlMapping) throws Exception{
		return new ResponseEntity<Object>(azureService.writeStatusByUrl(urlMapping.getUrl()), OK);
	}

}
