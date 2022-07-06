package com.prograd.alok.Employee.Management.Controllers;

import com.prograd.alok.Employee.Management.Exceptions.IdMisMatchException;
import com.prograd.alok.Employee.Management.Exceptions.RoleNotFoundException;
import com.prograd.alok.Employee.Management.Models.Role;
import com.prograd.alok.Employee.Management.Services.RoleService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("api/roles/")
@AllArgsConstructor
@NoArgsConstructor
public class RollController {
    @Autowired
    RoleService roleService;

    @PostMapping("new")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        return new ResponseEntity<Role>(roleService.saveRole(role), HttpStatus.CREATED);
    }
    @GetMapping("all")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateRole(@RequestBody Role role, @PathVariable("id") Long emp_id){
        Role verifiedRole;
        try {
            verifiedRole=roleService.updateRole(role,emp_id);
        } catch (IdMisMatchException e) {
            return new ResponseEntity<String>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Role>(verifiedRole,HttpStatus.OK);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") Long emp_id ){
        Role role;
        try{
            role=roleService.getRoleById(emp_id);
        }catch (RoleNotFoundException e){
            return new ResponseEntity<String>("Role deletion Failed",HttpStatus.NOT_FOUND);
        }
    try{
        roleService.deleteRole(emp_id);
    }catch (DataIntegrityViolationException e){
        return new ResponseEntity<String>("Role deletion Failed due to integrity constraints",HttpStatus.FORBIDDEN);
    }


        try{
            role=roleService.getRoleById(emp_id);
        }catch (RoleNotFoundException e){
            return new ResponseEntity<String>("Role deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<String>("Role deletion Failed",HttpStatus.METHOD_NOT_ALLOWED);

    }
    @GetMapping("{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Long emp_id){
        return new ResponseEntity<Role>(roleService.getRoleById(emp_id),HttpStatus.OK);
    }
}
