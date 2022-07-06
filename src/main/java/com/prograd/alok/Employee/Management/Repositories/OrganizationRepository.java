package com.prograd.alok.Employee.Management.Repositories;
import com.prograd.alok.Employee.Management.Models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}