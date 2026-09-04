package com.raulrobles.employeeapi.employee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.raulrobles.employeeapi.exception.EmployeeNotFoundException;

import org.openapitools.jackson.nullable.JsonNullable;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Raul")
                .lastName("Robles")
                .age(26)
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1999, 11, 12))
                .position("Java Developer")
                .createDate(LocalDateTime.now())
                .isActive(true)
                .build();
    }

    @Test
    void getEmployeeByIdReturnsTheRegisteredEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(1L);

        assertThat(result)
                .extracting(Employee::getId, Employee::getFirstName, Employee::getLastName,
                        Employee::getPosition, Employee::getIsActive)
                .containsExactly(1L, "Raul", "Robles", "Java Developer", true);
        verify(employeeRepository).findById(1L);
    }

    @Test
    void getEmployeeByIdThrowsWhenItDoesNotExist() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(99L))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Employee not found with id: 99");
    }

    @Test
    void createEmployeesTrimsNamesBeforePersistingThem() {
        EmployeeRequest request = new EmployeeRequest(" Raul ", null, " Robles ", null,
                26, Gender.MALE, LocalDate.of(1999, 11, 12), "Java Developer", true);
        when(employeeRepository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));

        List<Employee> result = employeeService.createEmployees(List.of(request));

        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(createdEmployee -> {
                    assertThat(createdEmployee.getFirstName()).isEqualTo("Raul");
                    assertThat(createdEmployee.getLastName()).isEqualTo("Robles");
                    assertThat(createdEmployee.getCreateDate()).isNotNull();
                    assertThat(createdEmployee.getIsActive()).isTrue();
                });
        verify(employeeRepository).saveAll(any());
    }

    @Test
    void updateEmployeeChangesOnlyProvidedFields() {
        EmployeeUpdateRequest request = new EmployeeUpdateRequest();
        request.setFirstName(JsonNullable.of(" Raúl "));
        request.setPosition(JsonNullable.of("Senior Java Developer"));
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.updateEmployee(1L, request);

        assertThat(result)
                .extracting(Employee::getFirstName, Employee::getLastName, Employee::getPosition,
                        Employee::getAge)
                .containsExactly("Raúl", "Robles", "Senior Java Developer", 26);
        verify(employeeRepository).save(employee);
    }

    @Test
    void deleteEmployeeDeletesExistingEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).delete(employee);
    }

    @Test
    void searchEmployeesDelegatesToRepositoryWithPagination() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Employee> expected = new PageImpl<>(List.of(employee), pageable, 1);
        when(employeeRepository.findByPartialName(eq("raul"), eq(pageable))).thenReturn(expected);

        Page<Employee> result = employeeService.searchEmployeesByPartialName("raul", pageable);

        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(foundEmployee -> {
                    assertThat(foundEmployee.getFirstName()).isEqualTo("Raul");
                    assertThat(foundEmployee.getLastName()).isEqualTo("Robles");
                });
    }
}
