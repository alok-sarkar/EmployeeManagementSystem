package com.prograd.alok.Employee.Management.Exceptions;

public class AssetNotFoundException extends RuntimeException {
    Long asset_id;
    public AssetNotFoundException(Long assetId) {
        super("Asset not found");
        this.asset_id =assetId;
    }
}
