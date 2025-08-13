package com.skpijtk.springboot_boilerplate.service.admin;

import com.skpijtk.springboot_boilerplate.constant.ResponseMessage;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.SystemSettingsDTO;
import com.skpijtk.springboot_boilerplate.model.AppSettings;
import com.skpijtk.springboot_boilerplate.repository.AppSettingRepository;
import com.skpijtk.springboot_boilerplate.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SystemSettingsService {

    @Autowired
    private AppSettingRepository appSettingRepository;

    public ResponseDTO<SystemSettingsDTO> getSystemSetting() {
        try {
            Optional<AppSettings> appSettings = appSettingRepository.findById(1L);
        
            SystemSettingsDTO data = new SystemSettingsDTO();
            data.setDefaultCheckInTime(appSettings.get().getDefaultCheckInTime());
            data.setDefaultCheckOutTime(appSettings.get().getDefaultCheckOutTime());
            data.setCheckInLateToleranceMinutes(appSettings.get().getCheckInLateToleranceMinutes());

            return ResponseUtil.build(ResponseMessage.FILTER_SUCCESS, data);
        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.FILTER_FAIL, null);
        }

    }

    public ResponseDTO<SystemSettingsDTO> updateSystemSetting(SystemSettingsDTO request) {
        try {
            Optional<AppSettings> appSettings = appSettingRepository.findById(1L);
            if (appSettings.isEmpty()) return ResponseUtil.build(ResponseMessage.SEARCH_FAIL, null);

            AppSettings settings = appSettings.get();

            settings.setDefaultCheckInTime(request.getDefaultCheckInTime());
            settings.setDefaultCheckOutTime(request.getDefaultCheckOutTime());
            settings.setCheckInLateToleranceMinutes(request.getCheckInLateToleranceMinutes());

            appSettingRepository.save(settings);

            SystemSettingsDTO data = new SystemSettingsDTO(settings.getDefaultCheckInTime(), settings.getDefaultCheckOutTime(), settings.getCheckInLateToleranceMinutes());
            return ResponseUtil.build(ResponseMessage.SAVE_SUCCESS, data);
        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.SAVE_FAIL, null);
        }
    }
}
