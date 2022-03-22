package com.example.pds.util.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String msg){
        super(msg);
    }
}
