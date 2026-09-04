package com.raulrobles.employeeapi.employee;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.openapitools.jackson.nullable.JsonNullable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeUpdateRequest {

    @NotBlank(message = "First name must not be null or blank when provided")
    @Size(max = 32, message = "First name must not exceed 32 characters")
    private JsonNullable<String> firstName = JsonNullable.undefined();

    @Pattern(regexp = ".*\\S.*", message = "Second name must not be blank when provided")
    @Size(max = 32, message = "Second name must not exceed 32 characters")
    private JsonNullable<String> secondName = JsonNullable.undefined();
    
    @NotBlank(message = "Last name must not be null or blank when provided")
    @Size(max = 32, message = "Last name must not exceed 32 characters")
    private JsonNullable<String> lastName = JsonNullable.undefined();

    @Pattern(regexp = ".*\\S.*", message = "Second last name must not be blank when provided")
    @Size(max = 32, message = "Second last name must not exceed 32 characters")
    private JsonNullable<String> secondLastName = JsonNullable.undefined();

    @NotNull(message = "Age must not be null when provided")
    @Min(value = 18, message = "Age must be greater than or equal to 18")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    private JsonNullable<Integer> age = JsonNullable.undefined();

    @NotNull(message = "Gender is required")
    private JsonNullable<Gender> gender = JsonNullable.undefined();

    @NotNull(message = "Birth date must not be null when provided")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private JsonNullable<LocalDate> birthDate = JsonNullable.undefined();

    @Size(max = 50, message = "Position must not exceed 50 characters")
    @NotBlank(message = "Position must not be null or blank when provided")
    private JsonNullable<String> position = JsonNullable.undefined();

    @NotNull(message = "isActive is required")
    private JsonNullable<Boolean> isActive = JsonNullable.undefined();
}