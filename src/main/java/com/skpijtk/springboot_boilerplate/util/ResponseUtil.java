package com.skpijtk.springboot_boilerplate.util;

import com.skpijtk.springboot_boilerplate.constant.ResponseMessage;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;

public class ResponseUtil {
    
    public static String getHttpStatusText(int statusCode) {
        return switch (statusCode) {
            case 200 -> "OK";
            case 201 -> "Created";
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 404 -> "Not Found";
            case 409 -> "Conflict";
            case 500 -> "Internal Server Error";
            default -> "Unknown Status";
        };
    }
    public static <T> ResponseDTO<T> build(ResponseMessage msg, T data) {
        return ResponseDTO.<T>builder()
                .data(data)
                .message(msg.getCode())
                .status(msg.getMessage())
                .statusCode(msg.getStatusCode())
                .build();
    }

    public static <T> ResponseDTO<T> buildWithMessage(ResponseMessage msg, T data, String customMessage) {
        return ResponseDTO.<T>builder()
                .data(data)
                .message(msg.getCode())
                .status(customMessage)
                .statusCode(msg.getStatusCode())
                .build();
    }
}

