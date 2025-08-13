package com.skpijtk.springboot_boilerplate.service.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skpijtk.springboot_boilerplate.constant.CheckInStatus;
import com.skpijtk.springboot_boilerplate.constant.ResponseMessage;
import com.skpijtk.springboot_boilerplate.constant.Role;
import com.skpijtk.springboot_boilerplate.dto.DTO.AttendanceDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.CheckinStudentDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.MhsDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.PaginationDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.request.AddMhsRequest;
import com.skpijtk.springboot_boilerplate.dto.request.GetListCheckinAllMahasiswaRequest;
import com.skpijtk.springboot_boilerplate.dto.request.UpdateMhsRequest;
import com.skpijtk.springboot_boilerplate.model.Attendance;
import com.skpijtk.springboot_boilerplate.model.Student;
import com.skpijtk.springboot_boilerplate.model.User;
import com.skpijtk.springboot_boilerplate.repository.AttendanceRepository;
import com.skpijtk.springboot_boilerplate.repository.StudentsRepository;
import com.skpijtk.springboot_boilerplate.repository.UserRepository;
import com.skpijtk.springboot_boilerplate.util.ResponseUtil;

@Service
public class MhsManagementService {

    @Autowired
    private StudentsRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Untuk pencarian berdasarkan nama (multiple student, pagination)
    public ResponseDTO<PaginationDTO<CheckinStudentDTO>> getListCheckinMahasiswa(GetListCheckinAllMahasiswaRequest request
    ) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Page<Student> studentPage;

        if (request.getStudent_name().isEmpty()) {
            studentPage = studentRepository.findAll(pageable);
        } else {
            studentPage = studentRepository.findFilteredStudents(request.getStudent_name(), pageable);
        }

        List<CheckinStudentDTO> studentDTOs = studentPage.getContent().stream().map(student -> {
            User user = student.getUser();
            
            List<Attendance> attendances = attendanceRepository.findFilteredAttendances(
                student.getId().longValue(), request.getStartdate(), request.getEnddate()
            );

            List<AttendanceDTO> listAttendanceDTO = attendances.stream().map(attendanceData ->
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

            return CheckinStudentDTO.builder()
                    .studentId(student.getId())
                    .userId(user.getId())
                    .studentName(user.getName())
                    .nim(student.getNim())
                    .email(user.getEmail())
                    .attendanceData(listAttendanceDTO)
                    .build();
        }).collect(Collectors.toList());


        PaginationDTO<CheckinStudentDTO> pagination = PaginationDTO.<CheckinStudentDTO>builder()
                .data(studentDTOs)
                .totalData(studentPage.getTotalElements())
                .totalPage(studentPage.getTotalPages())
                .currentPage(studentPage.getNumber())
                .pageSize(studentPage.getSize())
                .build();

        return ResponseUtil.build(ResponseMessage.FILTER_SUCCESS, pagination);
    }


    public ResponseDTO<MhsDTO> createStudent(AddMhsRequest request) {
        try {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setName(request.getStudentName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.MAHASISWA);
            user.setCreatedAt(LocalDateTime.now());
            user = userRepository.save(user);

            Student student = new Student();
            student.setNim(request.getNim());
            student.setUser(user);
            student.setCreatedAt(LocalDateTime.now());
            student = studentRepository.save(student);

            MhsDTO dto = new MhsDTO(student.getId().longValue(), user.getId().longValue(), user.getName(), student.getNim(), user.getEmail());
            return ResponseUtil.build(ResponseMessage.SAVE_SUCCESS, dto);
        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.SAVE_FAIL, null);
        }
    }

    public ResponseDTO<CheckinStudentDTO> getStudent(Long studentId) {
        try {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isEmpty()) return ResponseUtil.build(ResponseMessage.SEARCH_FAIL, null);

            Student student = optionalStudent.get();
            User user = student.getUser();

            List<Attendance> list_attendance = attendanceRepository.findByStudentId(studentId);

                List<AttendanceDTO> list_attendance_dto = list_attendance.stream().map(attendanceData ->
                    new AttendanceDTO(
                        attendanceData.getId().longValue(),
                        attendanceData.getCheckInTime(),
                        attendanceData.getCheckOutTime(),
                        attendanceData.getAttendanceDate(),
                        false,
                        attendanceData.getCheckInNotes(),
                        attendanceData.getCheckOutNotes(),
                        attendanceData.getCheckInStatus().toString()
                    )
                ).collect(Collectors.toList());

            CheckinStudentDTO dataMhs = new CheckinStudentDTO(student.getId(), user.getId(), user.getName(), student.getNim(), user.getEmail(), list_attendance_dto);

            return ResponseUtil.build(ResponseMessage.FILTER_SUCCESS, dataMhs);
        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.FILTER_FAIL, null);
        }
    }

    public ResponseDTO<MhsDTO> updateStudent(Long studentId, UpdateMhsRequest request) {
        try {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isEmpty()) return ResponseUtil.build(ResponseMessage.SEARCH_FAIL, null);

            Student student = optionalStudent.get();
            User user = student.getUser();

            user.setName(request.getStudentName());
            user.setEmail(request.getEmail());
            userRepository.save(user);

            student.setNim(request.getNim());
            studentRepository.save(student);

            MhsDTO dto = new MhsDTO(student.getId().longValue(), user.getId().longValue(), user.getName(), student.getNim(), user.getEmail());
            return ResponseUtil.build(ResponseMessage.SAVE_SUCCESS, dto);
        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.SAVE_FAIL, null);
        }
    }

    public ResponseDTO<MhsDTO> deleteStudent(Long studentId) {
        try {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isEmpty()) return ResponseUtil.build(ResponseMessage.SEARCH_FAIL, null);

            Student student = optionalStudent.get();
            User user = student.getUser();
            List<Attendance> list_attendance = attendanceRepository.findByStudentId(studentId);

            attendanceRepository.deleteAll(list_attendance);
            studentRepository.delete(student);
            userRepository.delete(user);

            MhsDTO dto = new MhsDTO(student.getId().longValue(), user.getId().longValue(), user.getName(), student.getNim(), user.getEmail());
            return ResponseUtil.build(ResponseMessage.DELETE_SUCCESS, dto);
        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.DELETE_FAIL, null);
        }
    }
}