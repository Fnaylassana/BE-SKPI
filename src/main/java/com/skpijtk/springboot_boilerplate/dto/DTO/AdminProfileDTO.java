package com.skpijtk.springboot_boilerplate.dto.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminProfileDTO  {
    private String name;
    private String role;
    private String time;
}