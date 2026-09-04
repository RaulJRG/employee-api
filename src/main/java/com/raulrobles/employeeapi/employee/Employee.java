package com.raulrobles.employeeapi.employee;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;

    @Column(name = "second_name", length = 32)
    private String secondName;

    @Column(name = "last_name", nullable = false, length = 32)
    private String lastName;

    @Column(name = "second_last_name", length = 32)
    private String secondLastName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 10)
    private Gender gender;
   
    //Debe ser formato dd-MM-yyyy
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "position", nullable = false, length = 50)
    private String position;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

}
