package com.gcastro.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@RequestMapping({"/hello"})
	public String firstPage() {
		return "Hello User World";
	}
	
	@RequestMapping({"/admin"})
	public String adminPage() {
		return "Hello Admin World";
	}
	
}
