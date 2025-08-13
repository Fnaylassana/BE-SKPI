package com.skpijtk.springboot_boilerplate.dto.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CiCoDTO {
    private Long studentId;
    private String studentName;
    private String nim;
    private Long attendanceId;
    private LocalDateTime checkinTime;
    private LocalDateTime checkOutTime;
    private LocalDate attendanceDate;
    private String notesCheckin;
    private String notesCheckout;
    private String statusCheckin;
}
    