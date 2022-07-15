package com.prograd.alok.Employee.Management.Services;

import com.prograd.alok.Employee.Management.Exceptions.AssetNotFoundException;
import com.prograd.alok.Employee.Management.Exceptions.IdMisMatchException;
import com.prograd.alok.Employee.Management.Models.Asset;
import com.prograd.alok.Employee.Management.Repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AssetService {
    @Autowired
    AssetRepository assetRepository;
    public Asset saveAsset(Asset asset){
        return assetRepository.save(asset);
    }
    public Asset getAssetById(Long assetId) throws  AssetNotFoundException{
        return assetRepository.findById(assetId).orElseThrow(()->new AssetNotFoundException(assetId));
    }
    public Asset updateAsset(Asset asset, Long assetId) throws IdMisMatchException{
        if(assetId.equals(asset.getAsset_id()))
            return assetRepository.save(asset);
        else
            throw new IdMisMatchException(assetId,asset.getAsset_id());
    }
    public List<Asset> getAllAssets(){
        return assetRepository.findAll();
    }
    public void deleteAsset(Long assetId){
        assetRepository.deleteById(assetId);
    }
}
