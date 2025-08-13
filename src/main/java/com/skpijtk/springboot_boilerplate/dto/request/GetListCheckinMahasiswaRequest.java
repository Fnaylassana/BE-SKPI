package com.skpijtk.springboot_boilerplate.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetListCheckinMahasiswaRequest {
    private Long student_id;  
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startdate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate enddate;
    private int page = 0;
    private int size = 10;
}
