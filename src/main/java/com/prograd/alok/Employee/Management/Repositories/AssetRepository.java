package com.prograd.alok.Employee.Management.Repositories;

import com.prograd.alok.Employee.Management.Models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}