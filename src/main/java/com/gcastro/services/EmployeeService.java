package com.gcastro.services;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<Employee> findAll() { 
		return (List<Employee>)employeeRepository.findAll();
	}
	
	public Employee findById(long eid) { 
		return employeeRepository.findById(eid).orElse(null);
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
	
	public Employee update(long eid, Employee e) {
		e.setId(eid);	
		return employeeRepository.save(e);
	}
	
	public void delete(long eid) { 
		employeeRepository.deleteById(eid);
	}
	
}
