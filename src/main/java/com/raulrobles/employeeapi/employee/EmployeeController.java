package com.raulrobles.employeeapi.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
    
    @PostMapping
    public ResponseEntity<List<Employee>> createEmployees(@Valid @RequestBody EmployeesRequest request) {
        List<Employee> employeesCreated = this.employeeService.createEmployees(request.employees());
        return ResponseEntity.status(HttpStatus.CREATED).body(employeesCreated);
    }
    
}
