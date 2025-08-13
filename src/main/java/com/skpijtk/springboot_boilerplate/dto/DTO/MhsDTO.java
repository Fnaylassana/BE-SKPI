package com.skpijtk.springboot_boilerplate.dto.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MhsDTO  {
    private Long studentId;
    private Long userId;
    private String studentName;
    private String nim;
    private String email;
}