package com.raulrobles.employeeapi.employee;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raulrobles.employeeapi.exception.EmployeeNotFoundException;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves a page of employees.
     * @param pageable pagination and sorting information
     * @return the requested page of employees
     */
    @Transactional(readOnly = true)
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return this.employeeRepository.findAll(pageable);
    }

    /**
     * Searches employees by a partial, case-insensitive name match.
     * @param name value to be filtered
     * @param pageable pagination and sorting information
     * @return a page with the employees matched
     */
    @Transactional(readOnly = true)
    public Page<Employee> searchEmployeesByPartialName(String name, Pageable pageable) {
        return this.employeeRepository.findByPartialName(name, pageable);
    }

    /**
     * Retrieves an employee by its identifier.
     *
     * @param id employee identifier
     * @return the employee associated with the given identifier
     * @throws EmployeeNotFoundException if no employee exists with the given identifier
     */
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return this.employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    /**
     * Creates and persists Employees entities from validated request data
     * @param employeesRequest validated employee data
     * @return the persisted employees, including their generated identifiers
     */
    @Transactional
    public List<Employee> createEmployees(List<EmployeeRequest> employeesRequest){
        List<Employee> employees = employeesRequest.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        return this.employeeRepository.saveAll(employees);
    }

    /**
     * Updates an existing Employee entity in database
     * @param id employee identifier
     * @param employeeUpdateRequest validated employee data
     * @return Employee entity updated
     * @throws EmployeeNotFoundException if no employee exists with the given identifier
     */
    @Transactional
    public Employee updateEmployee(Long id, EmployeeUpdateRequest employeeUpdateRequest) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        this.updateEntity(employee, employeeUpdateRequest);
        return this.employeeRepository.save(employee);
    }

    /**
     * Deletes an existing Employee entity from database
     * @param id employee identifier
     * @throws EmployeeNotFoundException if no employee exists with the given identifier
     */
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        this.employeeRepository.delete(employee);
    }

    /**
     * Maps the employee request to a new Employee entity
     * @param employeeRequest validated employee data
     * @return new Employee entity ready to be persisted
     */
    private Employee mapToEntity(EmployeeRequest employeeRequest) {
        return Employee.builder()
                .firstName(employeeRequest.firstName().trim())
                .secondName(trimNullable(employeeRequest.secondName()))
                .lastName(employeeRequest.lastName().trim())
                .secondLastName(trimNullable(employeeRequest.secondLastName()))
                .age(employeeRequest.age())
                .gender(employeeRequest.gender())
                .birthDate(employeeRequest.birthDate())
                .position(employeeRequest.position())
                .createDate(LocalDateTime.now())
                .isActive(employeeRequest.isActive())
                .build();
    }

    /**
     * Updates partial or totally an existing Employee entity with validated request data
     * @param employee Entity to be updated
     * @param employeeUpdateRequest validated employee data
     */
    private void updateEntity(Employee employee, EmployeeUpdateRequest employeeUpdateRequest) {
        employeeUpdateRequest.getFirstName().ifPresent(name -> employee.setFirstName(name.trim()));
        employeeUpdateRequest.getSecondName().ifPresent(name -> employee.setSecondName(trimNullable(name)));
        employeeUpdateRequest.getLastName().ifPresent(lastName -> employee.setLastName(trimNullable(lastName)));
        employeeUpdateRequest.getSecondLastName().ifPresent(lastName -> employee.setSecondLastName(trimNullable(lastName)));
        employeeUpdateRequest.getAge().ifPresent(age -> employee.setAge(age));
        employeeUpdateRequest.getGender().ifPresent(gender -> employee.setGender(gender));
        employeeUpdateRequest.getBirthDate().ifPresent(birthDate -> employee.setBirthDate(birthDate));
        employeeUpdateRequest.getPosition().ifPresent(position -> employee.setPosition(position));
        employeeUpdateRequest.getIsActive().ifPresent(isActive -> employee.setIsActive(isActive));
    }

    /**
     * Trims a nullable string
     * @param value string to trim
     * @return the string trimmed or null if the input is null
     */
    private String trimNullable(String value){
        return value == null ? null : value.trim();
    }
}
