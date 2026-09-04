package com.raulrobles.employeeapi.employee;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public record EmployeesRequest(
    @NotEmpty(message = "The list of employees cannot be empty")
    List<@Valid EmployeeRequest> employees) {
}
