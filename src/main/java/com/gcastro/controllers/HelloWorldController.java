package com.gcastro.controllers;

import java.nio.file.FileAlreadyExistsException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcastro.entities.Employee;
import com.gcastro.entities.EmployeeDTO;
import com.gcastro.services.EmployeeService;

@RestController
public class HelloWorldController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping({"/hello"})
	public String firstPage() {
		return "Hello User World";
	}
	
	@RequestMapping({"/admin"})
	public String adminPage() {
		return "Hello Admin World";
	}
	
	@PostMapping("/createEmployee")
	public Employee insert(@Valid @RequestBody EmployeeDTO e) throws FileAlreadyExistsException {
	return employeeService.insert(e);
	}
	
	@PostMapping("/modifyEmployee")
	public Employee update(@Valid @RequestBody EmployeeDTO e, HttpServletRequest request) throws FileAlreadyExistsException {
		return employeeService.update(e, request);
	}
	
	
	
}
