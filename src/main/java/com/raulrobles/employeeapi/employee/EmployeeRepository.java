package com.raulrobles.employeeapi.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""      
        SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
        OR LOWER(e.secondName) LIKE LOWER(CONCAT('%', :name, '%'))
        OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :name, '%'))
        OR LOWER(e.secondLastName) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    public Page<Employee> findByPartialName(@Param("name") String name, Pageable pageable);

}
