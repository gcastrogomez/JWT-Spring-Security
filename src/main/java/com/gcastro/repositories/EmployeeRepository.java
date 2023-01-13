package com.gcastro.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gcastro.entities.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
}
