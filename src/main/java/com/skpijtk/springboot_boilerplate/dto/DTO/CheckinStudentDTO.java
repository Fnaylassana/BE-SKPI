package com.skpijtk.springboot_boilerplate.dto.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckinStudentDTO {
    private Integer studentId;
    private Integer userId;
    private String studentName;
    private String nim;
    private String email;
    private List<AttendanceDTO> attendanceData;
}
