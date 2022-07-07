package com.prograd.alok.Employee.Management.Services;

import com.prograd.alok.Employee.Management.Exceptions.EmployeeNotFoundException;
import com.prograd.alok.Employee.Management.Exceptions.IdMisMatchException;
import com.prograd.alok.Employee.Management.Models.Employee;
import com.prograd.alok.Employee.Management.Models.Role;
import com.prograd.alok.Employee.Management.Repositories.EmployeeRepository;
import com.prograd.alok.Employee.Management.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long emp_id) throws EmployeeNotFoundException{
        return employeeRepository.findById(emp_id).orElseThrow(()->new EmployeeNotFoundException(emp_id));
    }

    public Employee updateEmployee(Employee employee, Long employeeId) throws  IdMisMatchException{
        if(employee.getEmp_id().equals(employeeId)){
            List<Role> roleList=employee.getRoles();
//        for (Role role : roleList) {
//            if(!roleRepository.findById(role.getId()).isPresent()){
//                Role role1= new Role();
//                role1.setId(role.getId());
//                role1.setRoleName(role.getRoleName());
//                roleRepository.save(role1);
//            }
//        }
            Iterator<Role> itr=roleList.listIterator();
            while (itr.hasNext()){
                Role role=(Role)itr.next();
                if(!roleRepository.findById(role.getId()).isPresent()){
                    itr.remove();
                }
            }
//        for (Role role : roleList) {
//            if(!roleRepository.findById(role.getId()).isPresent()){
//                System.out.println(role.getId());
//                roleList.remove(role);
//            }
//            System.out.println(role.getId());
//        }
//            System.out.println(roleList);
            employee.setRoles(roleList);
            return employeeRepository.save(employee);
        }
        else
                throw new IdMisMatchException(employeeId,employee.getEmp_id());

//        System.out.println("Ok it is calling");
//        if(employee.getEmp_id().equals(employeeId))
//            return employeeRepository.save(employee);
//        else
//                throw new IdMisMatchException(employeeId,employee.getEmp_id());
    }
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    public void deleteEmployee(Long emp_id){
        employeeRepository.deleteById(emp_id);
    }
}
