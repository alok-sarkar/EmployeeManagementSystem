package com.prograd.alok.Employee.Management.Services;
import com.prograd.alok.Employee.Management.Exceptions.IdMisMatchException;
import com.prograd.alok.Employee.Management.Exceptions.RoleNotFoundException;
import com.prograd.alok.Employee.Management.Models.Role;
import com.prograd.alok.Employee.Management.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public Role getRoleById(Long role_id) throws  RoleNotFoundException{
        return roleRepository.findById(role_id).orElseThrow(()->new RoleNotFoundException(role_id));
    }
    public Role updateRole(Role role, Long roleId) throws IdMisMatchException  {
        if(role.getId().equals(roleId))
            return roleRepository.save(role);
        else
            throw new IdMisMatchException(roleId,role.getId());
    }
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
    public void deleteRole(Long role_id) throws DataIntegrityViolationException {
        roleRepository.deleteById(role_id);
    }
}
