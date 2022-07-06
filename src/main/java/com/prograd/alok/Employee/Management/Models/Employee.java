package com.prograd.alok.Employee.Management.Models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Data
@Table
@NoArgsConstructor
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long emp_id;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
    private String password;
    @ManyToMany(targetEntity = Role.class,fetch = FetchType.EAGER)
    private List<Role> roles= new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //        List<GrantedAuthority> authorities=roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//        List<GrantedAuthority> authorities=Arrays.stream(this.roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return roles.stream().map((role)->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Long emp_id) {
        this.emp_id = emp_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
