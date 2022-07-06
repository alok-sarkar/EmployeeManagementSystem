package com.prograd.alok.Employee.Management.Exceptions;

public class IdMisMatchException extends Exception{
    Long urlId;
    Long objectId;
    public IdMisMatchException(Long urlId, Long objectId) {
        super("Ids didn't match");
        this.urlId=urlId;
        this.objectId=objectId;
    }
}
