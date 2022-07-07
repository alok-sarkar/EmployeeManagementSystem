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
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/employee/")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("new")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee){
        if(employee.getEmail().isEmpty() || employee.getFirstName().isEmpty()|| employee.getLastName().isEmpty()||employee.getPassword().isEmpty()){
            return new ResponseEntity<>("Please Fill the required fields: firstname,lastname,email,password",HttpStatus.FORBIDDEN);
        }
        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(employee.getEmail()).matches()){
            return  new ResponseEntity<>("Invalid Email",HttpStatus.FORBIDDEN);
        }
        if(employee.getPassword().length()<8)
            return new ResponseEntity<>("Password too short, it must have length of 8 or greater",HttpStatus.FORBIDDEN);
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
            if(authentication.getName().equals(employee.getEmail()) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("HR"))){
                if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("HR")))
                    employee.setRoles(employeeService.getEmployeeById(employee.getEmp_id()).getRoles());
                verifiedEmployee=employeeService.updateEmployee(employee,emp_id);
            }
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
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long emp_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals(employeeService.getEmployeeById(emp_id).getEmail()) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("HR")) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("Admin")))
           try{
               return new ResponseEntity<Employee>(employeeService.getEmployeeById(emp_id),HttpStatus.OK);
           }catch (EmployeeNotFoundException e){
               return new ResponseEntity<String>("Employee not found",HttpStatus.NOT_FOUND);
           }
        else
            return new ResponseEntity<String>("Unauthorized access Request ",HttpStatus.UNAUTHORIZED);
    }
}
