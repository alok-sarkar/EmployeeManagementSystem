package com.prograd.alok.Employee.Management.Repositories;

import com.prograd.alok.Employee.Management.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}