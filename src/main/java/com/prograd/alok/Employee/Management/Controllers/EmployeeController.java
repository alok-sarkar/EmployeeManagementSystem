package com.prograd.alok.Employee.Management.Controllers;

import com.prograd.alok.Employee.Management.Exceptions.EmployeeNotFoundException;
import com.prograd.alok.Employee.Management.Exceptions.IdMisMatchException;
import com.prograd.alok.Employee.Management.Models.Employee;
import com.prograd.alok.Employee.Management.Services.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/employee/")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("new")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("all")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable("id") Long emp_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee verifiedEmployee;
        try {
            if(authentication.getName().equals(employee.getEmail()) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("HR")))
                verifiedEmployee=employeeService.updateEmployee(employee,emp_id);
            else
                return new ResponseEntity<String>("Unauthorized access Request ",HttpStatus.UNAUTHORIZED);
        } catch (IdMisMatchException e) {
            return new ResponseEntity<String>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Employee>(verifiedEmployee,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long emp_id ){
        Employee employee;
        try{
            employee=employeeService.getEmployeeById(emp_id);
        }catch (EmployeeNotFoundException e){
            return new ResponseEntity<String>("Employee deletion Failed",HttpStatus.NOT_FOUND);
        }

        employeeService.deleteEmployee(emp_id);
        try{
            employee=employeeService.getEmployeeById(emp_id);
        }catch (EmployeeNotFoundException e){
            return new ResponseEntity<String>("Employee deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<String>("Employee deletion Failed",HttpStatus.METHOD_NOT_ALLOWED);

    }
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long emp_id){
        return new ResponseEntity<Employee>(employeeService.getEmployeeById(emp_id),HttpStatus.OK);
    }
}
