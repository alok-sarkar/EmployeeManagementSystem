package com.prograd.alok.Employee.Management.Services;

import com.prograd.alok.Employee.Management.Exceptions.EmployeeNotFoundException;
import com.prograd.alok.Employee.Management.Models.Employee;
import com.prograd.alok.Employee.Management.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeDetailsService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email).orElseThrow(()-> new EmployeeNotFoundException(email));
    }
}
