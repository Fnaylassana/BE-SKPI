package com.skpijtk.springboot_boilerplate.dto.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO<T> {
    private T data;
    private String message;
    private int statusCode;
    private String status;
}
