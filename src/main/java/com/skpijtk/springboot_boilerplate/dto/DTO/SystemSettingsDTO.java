package com.skpijtk.springboot_boilerplate.dto.DTO;

import java.time.LocalTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemSettingsDTO {
    private LocalTime defaultCheckInTime;
    private LocalTime defaultCheckOutTime;
    private Integer checkInLateToleranceMinutes;
}