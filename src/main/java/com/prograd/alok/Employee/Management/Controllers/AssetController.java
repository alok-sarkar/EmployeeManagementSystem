package com.prograd.alok.Employee.Management.Controllers;

import com.prograd.alok.Employee.Management.Exceptions.IdMisMatchException;
import com.prograd.alok.Employee.Management.Exceptions.AssetNotFoundException;
import com.prograd.alok.Employee.Management.Models.Asset;
import com.prograd.alok.Employee.Management.Services.AssetService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/assets/")
@AllArgsConstructor
@NoArgsConstructor
public class AssetController {
    @Autowired
    AssetService assetService;

    @PostMapping("new")
    public ResponseEntity<Asset> saveAsset(@RequestBody Asset asset){
        return new ResponseEntity<Asset>(assetService.saveAsset(asset), HttpStatus.CREATED);
    }
    @GetMapping("all")
    public List<Asset> getAllAssets(){
        return assetService.getAllAssets();
    }
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateAsset(@RequestBody Asset asset, @PathVariable("id") Long emp_id){
        Asset verifiedAsset;
        try {
            verifiedAsset=assetService.updateAsset(asset,emp_id);
        } catch (IdMisMatchException e) {
            return new ResponseEntity<String>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Asset>(verifiedAsset,HttpStatus.OK);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteAsset(@PathVariable("id") Long emp_id ){
        Asset asset;
        try{
            asset=assetService.getAssetById(emp_id);
        }catch (AssetNotFoundException e){
            return new ResponseEntity<String>("Asset deletion Failed",HttpStatus.NOT_FOUND);
        }
        try{
            assetService.deleteAsset(emp_id);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<String>("Asset deletion Failed due to integrity constraints",HttpStatus.FORBIDDEN);
        }


        try{
            asset=assetService.getAssetById(emp_id);
        }catch (AssetNotFoundException e){
            return new ResponseEntity<String>("Asset deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<String>("Asset deletion Failed",HttpStatus.METHOD_NOT_ALLOWED);

    }
    @GetMapping("{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable("id") Long emp_id){
        return new ResponseEntity<Asset>(assetService.getAssetById(emp_id),HttpStatus.OK);
    }
}
