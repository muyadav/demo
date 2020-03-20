package com.example.demo.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	
	@GetMapping("/hello")
	public ResponseEntity<Object> getHello(){
		return new ResponseEntity<Object>("Hello There", OK);
	}

}
