package com.prograd.alok.Employee.Management.Services;

import com.prograd.alok.Employee.Management.Exceptions.IdMisMatchException;
import com.prograd.alok.Employee.Management.Exceptions.OrganizationNotFoundException;
import com.prograd.alok.Employee.Management.Models.Organization;
import com.prograd.alok.Employee.Management.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrgService {
    @Autowired
    OrganizationRepository organizationRepository;
    public Organization saveOrganization(Organization organization){
        return organizationRepository.save(organization);
    }
    public Organization getOrganizationById(Long organizationId){
        return organizationRepository.findById(organizationId).orElseThrow(()->new OrganizationNotFoundException(organizationId));
    }
    public Organization updateOrganization(Organization organization, Long organizationId) throws IdMisMatchException{
        if(organization.getOrg_id().equals(organizationId))
            return organizationRepository.save(organization);
        else
            throw new IdMisMatchException(organizationId,organization.getOrg_id());
    }
    public List<Organization> getAllOrganizations(){
        return organizationRepository.findAll();
    }
    public void deleteOrganization(Long organizationId){
        organizationRepository.deleteById(organizationId);
    }
}
