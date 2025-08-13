package com.skpijtk.springboot_boilerplate.service;

import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResumeCheckinDTO;

public interface AttendanceService {
    ResponseDTO<ResumeCheckinDTO> getResumeCheckin();
}
