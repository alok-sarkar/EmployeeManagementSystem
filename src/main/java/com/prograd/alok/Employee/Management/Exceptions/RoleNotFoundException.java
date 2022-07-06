package com.prograd.alok.Employee.Management.Exceptions;

public class RoleNotFoundException extends RuntimeException {
    Long id;
    public RoleNotFoundException(Long role_id) {
        super("Role not found");
        this.id=role_id;
    }
}
