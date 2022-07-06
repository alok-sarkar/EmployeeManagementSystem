package com.prograd.alok.Employee.Management.Exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    Long emp_id;
    String email;
    public EmployeeNotFoundException(Long emp_id) {
        super("User not found");
        this.emp_id=emp_id;
    }
    public EmployeeNotFoundException(String email) {
        super("User not found");
        this.email=email;
    }
}
