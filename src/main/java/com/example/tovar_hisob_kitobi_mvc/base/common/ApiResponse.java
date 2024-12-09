package com.example.tovar_hisob_kitobi_mvc.base.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Builder
public class ApiResponse<T> {
    private T data;
    private PaginationResponse pagination;
    private String message;
    private String responseCode;

    public static <T> ApiResponse<T> ok(){
        return ApiResponse.<T>builder()
                .responseCode(HttpStatus.OK.toString())
                .build();
    }

    public static <T> ApiResponse<T> ok(T data){
        return ApiResponse.<T>builder()
                .data(data)
                .responseCode(HttpStatus.OK.toString())
                .build();
    }

    public static <T> ApiResponse<T> ok(T data, PaginationResponse pagination){
        return ApiResponse.<T>builder()
                .data(data)
                .pagination(pagination)
                .responseCode(HttpStatus.OK.toString())
                .build();
    }

    public static <T> ApiResponse<T> error(T data, String message){
        return ApiResponse.<T>builder()
                .data(data)
                .responseCode(HttpStatus.OK.toString())
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> error(T data, String message, String responseCode){
        return ApiResponse.<T>builder()
                .data(data)
                .responseCode(responseCode)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> errorMessage(String message){
        return ApiResponse.<T>builder()
                .responseCode(HttpStatus.BAD_REQUEST.toString())
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> errorMessageAndResponseCode(String message, String responseCode){
        return ApiResponse.<T>builder()
                .responseCode(responseCode)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> errorResponseCode(String responseCode){
        return ApiResponse.<T>builder()
                .responseCode(responseCode)
                .build();
    }

}
