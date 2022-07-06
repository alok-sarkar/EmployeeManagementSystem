package com.prograd.alok.Employee.Management.Exceptions;

public class OrganizationNotFoundException extends RuntimeException {
    Long org_id;
    public OrganizationNotFoundException(Long organizationId) {
        super("Org not found");
        org_id=organizationId;
    }
}
