package com.raulrobles.employeeapi.employee;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public record EmployeeRequest (

    @NotBlank(message = "First name is required and must not be blank")
    @Size(max = 32, message = "First name must not exceed 32 characters")
    String firstName,

    @Pattern(regexp = ".*\\S.*", message = "Second name must not be blank")
    @Size(max = 32, message = "Second name must not exceed 32 characters")
    String secondName,

    @NotBlank(message = "Last name is required and must not be blank")
    @Size(max = 32, message = "Last name must not exceed 32 characters")
    String lastName,

    @Pattern(regexp = ".*\\S.*", message = "Second last name must not be blank")
    @Size(max = 32, message = "Second last name must not exceed 32 characters")
    String secondLastName,

    @NotNull(message = "Age is required")
    Integer age,

    //TODO: Validar con un enum para que solo acepte valores válidos (MALE, FEMALE, OTHER)
    String gender,
    
    @NotNull(message = "Birth date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate birthDate,

    @Size(max = 50, message = "Position must not exceed 50 characters")
    @NotBlank(message = "Position is required")
    String position,

    @NotNull(message = "isActive is required")
    Boolean isActive
){}