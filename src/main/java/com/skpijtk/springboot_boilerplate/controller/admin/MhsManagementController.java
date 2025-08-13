package com.skpijtk.springboot_boilerplate.controller.admin;

import com.skpijtk.springboot_boilerplate.dto.DTO.CheckinStudentDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.MhsDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.PaginationDTO;
import com.skpijtk.springboot_boilerplate.dto.DTO.ResponseDTO;
import com.skpijtk.springboot_boilerplate.dto.request.AddMhsRequest;
import com.skpijtk.springboot_boilerplate.dto.request.GetListCheckinAllMahasiswaRequest;
import com.skpijtk.springboot_boilerplate.dto.request.UpdateMhsRequest;
import com.skpijtk.springboot_boilerplate.service.admin.MhsManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class MhsManagementController {

    @Autowired
    private MhsManagementService mhsManagementService;

    @GetMapping("/list_all_mahasiswa")
    public ResponseDTO<PaginationDTO<CheckinStudentDTO>> getListAllMhs(@ModelAttribute GetListCheckinAllMahasiswaRequest request) {
        return mhsManagementService.getListCheckinMahasiswa(request);
    }

    // Add Mahasiswa
    @PostMapping("/add-mahasiswa")
    public ResponseDTO<MhsDTO> addMhs(@RequestBody AddMhsRequest request) {
        return mhsManagementService.createStudent(request);
    }

    // Get Mahasiswa by ID
    @GetMapping("/mahasiswa/{id_student}")
    public ResponseDTO<CheckinStudentDTO> getMhs(@PathVariable("id_student") Long studentId) {
        return mhsManagementService.getStudent(studentId);
    }

    // Edit Mahasiswa by ID
    @PutMapping("/edit-mahasiswa/{id_student}")
    public ResponseDTO<MhsDTO> updateMhs(@PathVariable("id_student") Long studentId,
                                         @RequestBody UpdateMhsRequest request) {
        return mhsManagementService.updateStudent(studentId, request);
    }

    // Delete Mahasiswa by ID
    @DeleteMapping("/mahasiswa/{id_student}")
    public ResponseDTO<MhsDTO> deleteMhs(@PathVariable("id_student") Long studentId) {
        return mhsManagementService.deleteStudent(studentId);
    }

}
