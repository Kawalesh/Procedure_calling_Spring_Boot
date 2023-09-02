package com.rest.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.api.Repositoy.EmployeeRepository;
import com.rest.api.model.Employee;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getEmployees() {
		try {
			return new ResponseEntity<>(employeeRepository.getAllEmployees(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
		try {
			// check if employee exist in database
			Employee empObj = getEmpRec(id);

			if (empObj != null) {
				return new ResponseEntity<>(empObj, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable("id") long id) {
		try {
			// check if employee exist in database
			Employee emp = getEmpRec(id);

			if (emp != null) {
				employeeRepository.deleteEmployeeById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private Employee getEmpRec(long id) {
		Optional<Employee> empObj = Optional.ofNullable(employeeRepository.getEmployeeById(id));
		if (empObj.isPresent()) {
			return empObj.get();
		}
		return null;
	}

	@PostMapping("/employee")
	public ResponseEntity<Employee> newEmployee(@RequestBody Employee employee) {
		Employee newEmployee = employeeRepository
				.save(Employee.builder().name(employee.getName()).role(employee.getRole()).build());
		return new ResponseEntity<>(newEmployee, HttpStatus.OK);
	}

}
