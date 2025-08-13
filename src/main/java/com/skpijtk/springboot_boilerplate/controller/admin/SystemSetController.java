package com.skpijtk.springboot_boilerplate.controller.admin;

import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.SystemSettingsDTO;
import com.skpijtk.springboot_boilerplate.service.admin.SystemSettingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system-settings")
public class SystemSetController {

    @Autowired
    private SystemSettingsService systemSettingsService;

    @GetMapping
    public ResponseDTO<SystemSettingsDTO> getSystemSetting() {
        return systemSettingsService.getSystemSetting();
    }

    @PutMapping
    public ResponseDTO<SystemSettingsDTO> updateSystemSetting(@RequestBody SystemSettingsDTO request) {
        return systemSettingsService.updateSystemSetting(request);
    }

}
