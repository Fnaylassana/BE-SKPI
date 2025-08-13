package com.skpijtk.springboot_boilerplate.service.mahasiswa;

import com.skpijtk.springboot_boilerplate.constant.CheckInStatus;
import com.skpijtk.springboot_boilerplate.constant.ResponseMessage;
import com.skpijtk.springboot_boilerplate.dto.DTO.AttendanceDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.LoginDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.MahasiswaProfileDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.request.AuthRequest;
import com.skpijtk.springboot_boilerplate.model.Attendance;
import com.skpijtk.springboot_boilerplate.model.Student;
import com.skpijtk.springboot_boilerplate.model.User;
import com.skpijtk.springboot_boilerplate.repository.AttendanceRepository;
import com.skpijtk.springboot_boilerplate.repository.StudentsRepository;
import com.skpijtk.springboot_boilerplate.repository.UserRepository;
import com.skpijtk.springboot_boilerplate.security.JwtUtil;
import com.skpijtk.springboot_boilerplate.util.ResponseUtil;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class MahasiswaAuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public ResponseDTO<LoginDTO> login(AuthRequest request) {
        try {
            Optional<User> data_user = userRepository.findByEmail(request.getEmail());

            if (data_user.isPresent() && data_user.get().getRole().name().equals("MAHASISWA")) {
                User user = data_user.get();

                if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    LoginDTO loginDTO = LoginDTO.builder()
                            .idUser(user.getId())
                            .token(jwtUtil.generateToken(user))
                            .name(user.getName())
                            .role(user.getRole().name())
                            .build();

                    return ResponseUtil.build(ResponseMessage.LOGIN_SUCCESS, loginDTO);
                } else {
                    return ResponseUtil.build(ResponseMessage.INVALID_CREDENTIALS, null);
                }
            } else {
                return ResponseUtil.build(ResponseMessage.INVALID_CREDENTIALS, null);
            }
        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.INTERNAL_ERROR, null);
        }
    }
    public ResponseDTO<MahasiswaProfileDTO> getMahasiswaProfile(User user) {
        try {
            Optional<Student> student = studentsRepository.findByUserId(user.getId().longValue());

            if (student.isPresent()){

                List<Attendance> list_attendance = attendanceRepository.findByStudentId(student.get().getId().longValue());

                List<AttendanceDTO> list_attendance_dto = list_attendance.stream().map(attendanceData ->
                    new AttendanceDTO(
                        attendanceData.getId().longValue(),
                        attendanceData.getCheckInTime(),
                        attendanceData.getCheckOutTime(),
                        attendanceData.getAttendanceDate(),                   
                        attendanceData.getCheckInStatus() == CheckInStatus.TERLAMBAT,
                        attendanceData.getCheckInNotes(),
                        attendanceData.getCheckOutNotes(),
                        attendanceData.getCheckInStatus().toString()
                    )
                ).collect(Collectors.toList());

                MahasiswaProfileDTO data = MahasiswaProfileDTO.builder()
                            .studentId(student.get().getId().longValue())
                            .studentName(user.getName())
                            .nim(student.get().getNim())
                            .attendanceData(list_attendance_dto)
                            .build();

                return ResponseUtil.build(ResponseMessage.FILTER_SUCCESS, data);
            }

            return ResponseUtil.build(ResponseMessage.FILTER_FAIL, null);

        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.INTERNAL_ERROR, null);
        }
    }


}
