package com.raulrobles.employeeapi.employee;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raulrobles.employeeapi.exception.EmployeeNotFoundException;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(Long id) {
        return this.employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }   

    @Transactional
    public List<Employee> createEmployees(List<EmployeeRequest> employeesRequest){
        List<Employee> employees = employeesRequest.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        return this.employeeRepository.saveAll(employees);
    }

    private Employee mapToEntity(EmployeeRequest employeeRequest) {
        return Employee.builder()
                .firstName(employeeRequest.firstName())
                .secondName(employeeRequest.secondName())
                .lastName(employeeRequest.lastName())
                .secondLastName(employeeRequest.secondLastName())
                .age(employeeRequest.age())
                .gender(employeeRequest.gender())
                .birthDate(employeeRequest.birthDate())
                .position(employeeRequest.position())
                .createDate(LocalDateTime.now())
                .isActive(employeeRequest.isActive())
                .build();
    }
}
