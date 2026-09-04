package com.raulrobles.employeeapi.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> findAllEmployees(
            @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.employeeService.getAllEmployees(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Employee>> searchEmployeesByPartialName(
        @RequestParam String name,
        @PageableDefault(sort="firstName", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return ResponseEntity.ok(this.employeeService.searchEmployeesByPartialName(name, pageable));
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

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequest request) {
        return ResponseEntity.ok(this.employeeService.updateEmployee(id, request));
    }
    
}
