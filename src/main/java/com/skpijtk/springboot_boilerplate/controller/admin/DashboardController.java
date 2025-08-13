package com.skpijtk.springboot_boilerplate.controller.admin;

import com.skpijtk.springboot_boilerplate.dto.DTO.CheckinStudentDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.PaginationDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResumeCheckinDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.TotalMhsDTO;
import com.skpijtk.springboot_boilerplate.dto.request.GetListCheckinAllMahasiswaRequest;
import com.skpijtk.springboot_boilerplate.dto.request.GetListCheckinMahasiswaRequest;
import com.skpijtk.springboot_boilerplate.service.admin.DashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/total_mahasiswa")
    public ResponseDTO<TotalMhsDTO> getTotalMahasiswa() {
        return dashboardService.getTotalStudents();
    }

    @GetMapping("/resume_checkin")
    public ResponseDTO<ResumeCheckinDTO> getResumeCheckin() {
        return dashboardService.getResumeCheckin();
    }
    
    @GetMapping("/list_checkin_mahasiswa")
    public ResponseDTO<PaginationDTO<CheckinStudentDTO>> getListCheckin(@RequestParam(required = false) Long student_id,
                                                                        @ModelAttribute GetListCheckinAllMahasiswaRequest requestAll,
                                                                        @ModelAttribute GetListCheckinMahasiswaRequest requestOne) {
        if (student_id != null) {
            return dashboardService.getListCheckinMahasiswa(requestOne);
        } else {
            return dashboardService.getListCheckinAllMahasiswa(requestAll);
        }
    }

}
