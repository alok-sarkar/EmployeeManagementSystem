package com.prograd.alok.Employee.Management.Controllers;

import com.prograd.alok.Employee.Management.Exceptions.IdMisMatchException;
import com.prograd.alok.Employee.Management.Exceptions.OrganizationNotFoundException;
import com.prograd.alok.Employee.Management.Models.Organization;
import com.prograd.alok.Employee.Management.Services.OrgService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/org/")
@AllArgsConstructor
@NoArgsConstructor
public class OrgController {
    @Autowired
    OrgService orgService;

    @PostMapping("new")
    public ResponseEntity<Organization> saveOrganization(@RequestBody Organization org){
        return new ResponseEntity<Organization>(orgService.saveOrganization(org), HttpStatus.CREATED);
    }

    @GetMapping("all")
    public List<Organization> getAllOrganizations(){
        return orgService.getAllOrganizations();
    }
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateOrganization(@RequestBody Organization org, @PathVariable("id") Long emp_id){
        Organization verifiedOrganization;
        try {
            verifiedOrganization=orgService.updateOrganization(org,emp_id);
        } catch (IdMisMatchException e) {
            return new ResponseEntity<String>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Organization>(verifiedOrganization,HttpStatus.OK);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable("id") Long emp_id ){
        Organization org;
        try{
            org=orgService.getOrganizationById(emp_id);
        }catch (OrganizationNotFoundException e){
            return new ResponseEntity<String>("Organization deletion Failed",HttpStatus.NOT_FOUND);
        }
        try{
            orgService.deleteOrganization(emp_id);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<String>("Organization deletion Failed due to integrity constraints",HttpStatus.FORBIDDEN);
        }


        try{
            org=orgService.getOrganizationById(emp_id);
        }catch (OrganizationNotFoundException e){
            return new ResponseEntity<String>("Organization deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<String>("Organization deletion Failed",HttpStatus.METHOD_NOT_ALLOWED);

    }
    @GetMapping("{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable("id") Long emp_id){
        return new ResponseEntity<Organization>(orgService.getOrganizationById(emp_id),HttpStatus.OK);
    }
}
