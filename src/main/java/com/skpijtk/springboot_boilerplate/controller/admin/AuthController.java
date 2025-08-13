package com.skpijtk.springboot_boilerplate.controller.admin;

import com.skpijtk.springboot_boilerplate.dto.DTO.AdminProfileDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.LoginDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.RegisterDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.request.AuthRequest;
import com.skpijtk.springboot_boilerplate.dto.request.SignUpRequest;
import com.skpijtk.springboot_boilerplate.model.User;
import com.skpijtk.springboot_boilerplate.security.CurrentUser;
import com.skpijtk.springboot_boilerplate.service.admin.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseDTO<RegisterDTO> register(@RequestBody SignUpRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseDTO<LoginDTO> login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @GetMapping("/profile")
    public ResponseDTO<AdminProfileDTO> getAdminProfile(@CurrentUser User user) {
        return authService.getAdminProfile(user);
    }
}
