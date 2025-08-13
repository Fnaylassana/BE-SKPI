package com.skpijtk.springboot_boilerplate.dto.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

import com.skpijtk.springboot_boilerplate.constant.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO  {

    private Integer id;
    private String name;
    private String username;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
