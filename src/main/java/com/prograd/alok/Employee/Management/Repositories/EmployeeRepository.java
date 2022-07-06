package com.prograd.alok.Employee.Management.Repositories;

import com.prograd.alok.Employee.Management.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
}