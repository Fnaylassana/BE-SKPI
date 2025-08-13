package com.skpijtk.springboot_boilerplate.service.mahasiswa;

import com.skpijtk.springboot_boilerplate.constant.CheckInStatus;
import com.skpijtk.springboot_boilerplate.constant.ResponseMessage;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.request.CheckInRequest;
import com.skpijtk.springboot_boilerplate.dto.request.CheckOutRequest;
import com.skpijtk.springboot_boilerplate.model.AppSettings;
import com.skpijtk.springboot_boilerplate.model.Attendance;
import com.skpijtk.springboot_boilerplate.model.Student;
import com.skpijtk.springboot_boilerplate.model.User;
import com.skpijtk.springboot_boilerplate.repository.AppSettingRepository;
import com.skpijtk.springboot_boilerplate.repository.AttendanceRepository;
import com.skpijtk.springboot_boilerplate.repository.StudentsRepository;
import com.skpijtk.springboot_boilerplate.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class AttendanceMhsService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentsRepository studentRepository;

    @Autowired
    private AppSettingRepository appSettingsRepository;

    public ResponseDTO<Attendance> checkin(User user, CheckInRequest request) {
        try {
            Optional<Student> studentOpt = studentRepository.findByUserId(user.getId().longValue());
            if (studentOpt.isEmpty()) {
                return ResponseUtil.buildWithMessage(ResponseMessage.SEARCH_FAIL, null, "Student Not Found");
            }

            LocalDate today = LocalDate.now();
            Optional<Attendance> existingAttendance = attendanceRepository.findByStudentIdAndAttendanceDate(studentOpt.get().getId().longValue(), today);
            if (existingAttendance.isPresent()) {
                return ResponseUtil.buildWithMessage(ResponseMessage.CHECKIN_FAIL_ALREADY_EXIST, null, "You Already Checkin");
            }

            Optional<AppSettings> settings = appSettingsRepository.findById(1L);
            if (settings.isEmpty()) {
                return ResponseUtil.buildWithMessage(ResponseMessage.SEARCH_FAIL, null, "App Setting Not Found");
            }

            LocalTime defaultCheckInTime = settings.get().getDefaultCheckInTime();
            int toleranceMinutes = settings.get().getCheckInLateToleranceMinutes();

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expectedLatestCheckIn = now.with(defaultCheckInTime).plusMinutes(toleranceMinutes);

            // apakah terlambat
            boolean isLate = now.isAfter(expectedLatestCheckIn);

            Attendance attendance = new Attendance();
            attendance.setStudent(studentOpt.get());
            attendance.setAttendanceDate(today);
            attendance.setCheckInTime(LocalDateTime.now());
            attendance.setCheckInNotes(request.getNotesCheckin());
            attendance.setCheckInStatus(isLate ? CheckInStatus.TERLAMBAT : CheckInStatus.TEPAT_WAKTU);

            attendance = attendanceRepository.save(attendance);

            return ResponseUtil.buildWithMessage(ResponseMessage.CHECK_SUCCESS, attendance, "Checkin successful");

        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.INTERNAL_ERROR, null);
        }
    }

    public ResponseDTO<Attendance> checkout(User user, CheckOutRequest request) {
        try {
            LocalDate today = LocalDate.now();

            Optional<Student> dataStudent = studentRepository.findByUserId(user.getId().longValue());
            Optional<Attendance> attendanceOpt = attendanceRepository.findByStudentIdAndAttendanceDate(dataStudent.get().getId().longValue(), today);
            if (attendanceOpt.isEmpty()) {
                return ResponseUtil.build(ResponseMessage.CHECK_FAIL_NOTE_EMPTY, null);
            }

            Attendance attendance = attendanceOpt.get();
            if (attendance.getCheckOutTime() != null) {
                return ResponseUtil.buildWithMessage(ResponseMessage.CHECKIN_FAIL_ALREADY_EXIST, null, "You Already Checkout");
            }

            attendance.setCheckOutTime(LocalDateTime.now());
            attendance.setCheckOutNotes(request.getNotesCheckout());

            attendance = attendanceRepository.save(attendance);

            return ResponseUtil.buildWithMessage(ResponseMessage.CHECK_SUCCESS, attendance, "Checkout successful");

        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.INTERNAL_ERROR, null);
        }
    }
} 
