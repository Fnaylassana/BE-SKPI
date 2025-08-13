package com.skpijtk.springboot_boilerplate.controller.mahasiswa;

import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.request.CheckInRequest;
import com.skpijtk.springboot_boilerplate.dto.request.CheckOutRequest;
import com.skpijtk.springboot_boilerplate.model.Attendance;
import com.skpijtk.springboot_boilerplate.model.User;
import com.skpijtk.springboot_boilerplate.security.CurrentUser;
import com.skpijtk.springboot_boilerplate.service.mahasiswa.AttendanceMhsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mahasiswa")
public class AttendanceActController {

    @Autowired
    private AttendanceMhsService attendanceMhsService;

    @PostMapping("/checkin")
    public ResponseDTO<Attendance> addCheckinMhs(@CurrentUser User user, @RequestBody CheckInRequest request) {
        return attendanceMhsService.checkin(user, request);
    }

    @PostMapping("/checkout")
    public ResponseDTO<Attendance> addCheckoutMhs(@CurrentUser User user, @RequestBody CheckOutRequest request) {
        return attendanceMhsService.checkout(user, request);
    }
}

