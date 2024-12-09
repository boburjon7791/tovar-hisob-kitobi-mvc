package com.example.tovar_hisob_kitobi_mvc.base.exception;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}
