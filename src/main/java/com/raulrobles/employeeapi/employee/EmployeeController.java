package com.raulrobles.employeeapi.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.raulrobles.employeeapi.exception.ApiError;

import java.util.List;

import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employee API", description = "API for managing employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation (summary = "Get all employees", description = "Retrieves a page of employees with pagination and sorting options.")
    @ApiResponse (responseCode="200", description = "Successfully retrieved the list of employees")
    @GetMapping
    public ResponseEntity<Page<Employee>> findAllEmployees(
            @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.employeeService.getAllEmployees(pageable));
    }

    @Operation(summary = "Search employees by partial name", description = "Searches employees by a partial, case-insensitive name match.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid search parameter", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<Page<Employee>> searchEmployeesByPartialName(
        @RequestParam String name,
        @PageableDefault(sort="firstName", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return ResponseEntity.ok(this.employeeService.searchEmployeesByPartialName(name, pageable));
    }

    @Operation (summary = "Get employee by ID", description = "Retrieves an employee by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
    
    @Operation (summary = "Create employees", description = "Creates and persists Employees entities from validated request data.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employees created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid employee data", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<List<Employee>> createEmployees(@Valid @RequestBody EmployeesRequest request) {
        List<Employee> employeesCreated = this.employeeService.createEmployees(request.employees());
        return ResponseEntity.status(HttpStatus.CREATED).body(employeesCreated);
    }

    @Operation (summary = "Update employee by ID", description = "Updates an existing employee by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid employee data", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequest request) {
        return ResponseEntity.ok(this.employeeService.updateEmployee(id, request));
    }

    @Operation (summary = "Delete employee by ID", description = "Deletes an employee by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        this.employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
    
}
