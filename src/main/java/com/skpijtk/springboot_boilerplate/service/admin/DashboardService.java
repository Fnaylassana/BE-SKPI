package com.skpijtk.springboot_boilerplate.service.admin;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.skpijtk.springboot_boilerplate.constant.CheckInStatus;
import com.skpijtk.springboot_boilerplate.constant.ResponseMessage;
import com.skpijtk.springboot_boilerplate.dto.DTO.AttendanceDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.CheckinStudentDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.PaginationDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResumeCheckinDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.TotalMhsDTO;
import com.skpijtk.springboot_boilerplate.dto.request.GetListCheckinAllMahasiswaRequest;
import com.skpijtk.springboot_boilerplate.dto.request.GetListCheckinMahasiswaRequest;
import com.skpijtk.springboot_boilerplate.model.Attendance;
import com.skpijtk.springboot_boilerplate.model.Student;
import com.skpijtk.springboot_boilerplate.model.User;
import com.skpijtk.springboot_boilerplate.repository.AttendanceRepository;
import com.skpijtk.springboot_boilerplate.repository.StudentsRepository;
import com.skpijtk.springboot_boilerplate.util.ResponseUtil;

import org.springframework.stereotype.Service;

@Service
public class DashboardService{

    @Autowired
    private StudentsRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;
    

    public ResponseDTO<TotalMhsDTO> getTotalStudents() {
        try {
            long total = studentRepository.countTotalStudents();
            TotalMhsDTO data = new TotalMhsDTO(total);

            return ResponseUtil.build(ResponseMessage.FILTER_SUCCESS, data);
        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.FILTER_FAIL, null);

        }
    }


    public ResponseDTO<ResumeCheckinDTO> getResumeCheckin() {
        try {
            LocalDate today = LocalDate.now();

            long totalMahasiswa = studentRepository.countTotalStudents();
            long totalCheckin = attendanceRepository.countByAttendanceDateAndCheckInTimeIsNotNull(today);
            long totalTelatCheckin = attendanceRepository.countByAttendanceDateAndCheckInStatus(today, CheckInStatus.TERLAMBAT);
            long totalBelumCheckin = totalMahasiswa - totalCheckin;

            ResumeCheckinDTO data = new ResumeCheckinDTO(
                totalMahasiswa,
                totalCheckin,
                totalBelumCheckin,
                totalTelatCheckin
            );

            return ResponseUtil.build(ResponseMessage.FILTER_SUCCESS, data);
        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.FILTER_FAIL, null);
        }
    }

    public ResponseDTO<PaginationDTO<CheckinStudentDTO>> getListCheckinAllMahasiswa(GetListCheckinAllMahasiswaRequest request
    ) {
        try {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Page<Student> studentPage = studentRepository.findFilteredStudents(request.getStudent_name(), pageable);

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
        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.FILTER_FAIL, null);
        }
    }

    public ResponseDTO<PaginationDTO<CheckinStudentDTO>> getListCheckinMahasiswa(GetListCheckinMahasiswaRequest request
    ) {
        try {

            System.out.println("ini srudentdto"+request);
            Optional<Student> studentOpt = studentRepository.findById(request.getStudent_id());
            if (studentOpt.isEmpty()) {
                return ResponseUtil.build(ResponseMessage.FILTER_FAIL, null);
            }

            Student student = studentOpt.get();
            User user = student.getUser();

            List<Attendance> attendances = attendanceRepository.findFilteredAttendances(
                    student.getId().longValue(), request.getStartdate(), request.getEnddate()
            );

            
                    List<AttendanceDTO> list_attendance_dto = attendances.stream().map(attendanceData ->
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

            CheckinStudentDTO studentDTO = CheckinStudentDTO.builder()
                    .studentId(student.getId())
                    .userId(user.getId())
                    .studentName(user.getName())
                    .nim(student.getNim())
                    .email(user.getEmail())
                    .attendanceData(list_attendance_dto)
                    .build();

            PaginationDTO<CheckinStudentDTO> pagination = PaginationDTO.<CheckinStudentDTO>builder()
                    .data(List.of(studentDTO)) 
                    .totalData(1)
                    .totalPage(1)
                    .currentPage(0)
                    .pageSize(1)
                    .build();

            return ResponseUtil.build(ResponseMessage.FILTER_SUCCESS, pagination);
        } catch (Exception e) {
            return ResponseUtil.build(ResponseMessage.FILTER_FAIL, null);
        }
    }
}
