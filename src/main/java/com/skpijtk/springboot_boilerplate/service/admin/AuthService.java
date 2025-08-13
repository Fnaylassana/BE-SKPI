package com.skpijtk.springboot_boilerplate.service.admin;

import com.skpijtk.springboot_boilerplate.constant.ResponseMessage;
import com.skpijtk.springboot_boilerplate.constant.Role;
import com.skpijtk.springboot_boilerplate.dto.DTO.AdminProfileDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.LoginDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.RegisterDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.request.AuthRequest;
import com.skpijtk.springboot_boilerplate.dto.request.SignUpRequest;
import com.skpijtk.springboot_boilerplate.model.User;
import com.skpijtk.springboot_boilerplate.repository.UserRepository;
import com.skpijtk.springboot_boilerplate.security.JwtUtil;
import com.skpijtk.springboot_boilerplate.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseDTO<RegisterDTO> register(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseUtil.build(ResponseMessage.DUPLICATE_USERNAME_EMAIL, null);
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN); 

        userRepository.save(user);

        RegisterDTO data = new RegisterDTO(user.getEmail());

        return ResponseUtil.build(ResponseMessage.SIGNUP_SUCCESS, data);
    }


    public ResponseDTO<LoginDTO> login(AuthRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> {
                    LoginDTO loginDTO = LoginDTO.builder()
                            .idUser(user.getId())
                            .token(jwtUtil.generateToken(user))
                            .name(user.getName())
                            .role(user.getRole().name())
                            .build();

                    return ResponseUtil.build(ResponseMessage.LOGIN_SUCCESS, loginDTO);
                })
                .orElse(ResponseUtil.build(ResponseMessage.INVALID_CREDENTIALS, null));
    }


    public ResponseDTO<AdminProfileDTO> getAdminProfile(User user) {
        
        try {
            AdminProfileDTO data = AdminProfileDTO.builder()
                            .name(user.getName())
                            .role(user.getRole().name())
                            .time(user.getUpdatedAt().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")))
                            .build();

                return ResponseUtil.build(ResponseMessage.FILTER_SUCCESS, data);

        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.FILTER_FAIL, null);
        }
    }
}
