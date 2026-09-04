package com.raulrobles.employeeapi.employee;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public record EmployeeRequest (

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name must not be blank")
    String firstName,

    @NotBlank(message = "Second name must not be blank")
    String secondName,

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name must not be blank")
    String lastName,

    @NotBlank(message = "Second last name must not be blank")
    String secondLastName,

    @NotNull(message = "Age is required")
    Integer age,

    String gender,

    //Debe ser formato dd-MM-yyyy
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate birthDate,

    @Size(max = 50, message = "Position must not exceed 50 characters")
    String position,

    /*@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime createDate,
    */

    @NotNull(message = "isActive is required")
    Boolean isActive
){}