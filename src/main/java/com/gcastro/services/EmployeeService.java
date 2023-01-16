package com.gcastro.services;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcastro.config.JwtTokenUtil;
import com.gcastro.entities.Employee;
import com.gcastro.entities.EmployeeDTO;
import com.gcastro.entities.User;
import com.gcastro.repositories.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired 
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public List<Employee> findAll() { 
		return (List<Employee>)employeeRepository.findAll();
	}
	
	public Employee findById(long id) { 
		return employeeRepository.findById(id).orElse(null);
	}
	
	public Employee insert(EmployeeDTO e) throws FileAlreadyExistsException {
		User user= jwtUserDetailsService.save(e.getUserDTO());
		Employee employee=new Employee();
		employee.setName(e.getName());
		employee.setLastname(e.getLastname());
		employee.setEmail(e.getEmail());
		employee.setEmployee(user);
		return employeeRepository.save(employee);
	}
	
	public Employee update(EmployeeDTO e, HttpServletRequest request) throws FileAlreadyExistsException {
		final String jwtToken = request.getHeader("Authorization").substring(7);
		String id=null;
		Employee employee;
		id = jwtTokenUtil.getIdEmployeeFromToken(jwtToken);
		employee = findById(Long.parseLong(id));
		employee.setName(e.getName());
		employee.setLastname(e.getLastname());
		employee.setEmail(e.getEmail());
		User user= jwtUserDetailsService.findById(employee.getEmployee().getId());
		user.setPassword(e.getUserDTO().getPassword());
		employee.setEmployee(jwtUserDetailsService.save(user));
		return employeeRepository.save(employee);
	}
	
	public void delete(long eid) { 
		employeeRepository.deleteById(eid);
	}
	
}
