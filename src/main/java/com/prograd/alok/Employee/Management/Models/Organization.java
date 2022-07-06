package com.prograd.alok.Employee.Management.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.List;
@Entity
@Table
@Data
@NoArgsConstructor
public class Organization {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long org_id;
   private String name;
   @OneToMany(targetEntity = Employee.class,cascade = CascadeType.ALL)
   @JoinColumn(name = "FK_org_id")
   private List<Employee> employees;
   @OneToMany(targetEntity = Asset.class,cascade = CascadeType.ALL)
   @JoinColumn(name = "FK_org_id")
   private List<Asset> assets;
}
