package com.skpijtk.springboot_boilerplate.controller.mahasiswa;

import com.skpijtk.springboot_boilerplate.dto.DTO.LoginDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.MahasiswaProfileDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.request.AuthRequest;
import com.skpijtk.springboot_boilerplate.model.User;
import com.skpijtk.springboot_boilerplate.security.CurrentUser;
import com.skpijtk.springboot_boilerplate.service.mahasiswa.MahasiswaAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mahasiswa")
public class AuthMhsController {

    @Autowired
    private MahasiswaAuthService mhsAuthService;

    @PostMapping("/login")
    public ResponseDTO<LoginDTO> login(@RequestBody AuthRequest request) {
        return mhsAuthService.login(request);
    }

    @GetMapping("/profile")
    public ResponseDTO<MahasiswaProfileDTO> getMahasiswaProfile(@CurrentUser User user) {
        return mhsAuthService.getMahasiswaProfile(user);
    }
}
