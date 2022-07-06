package com.prograd.alok.Employee.Management.Models;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
@Entity
@Table
@Data
@NoArgsConstructor
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long asset_id;
    private String name;
    private Date issuedOn;
    private Date expireOn;
}
