package com.skpijtk.springboot_boilerplate.dto.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDTO {
    private Long attendanceId;
    private LocalDateTime checkinTime;
    private LocalDateTime checkoutTime;
    private LocalDate attendanceDate;
    private Boolean late;
    private String notesCheckin;
    private String notesCheckout;
    private String status;
}
