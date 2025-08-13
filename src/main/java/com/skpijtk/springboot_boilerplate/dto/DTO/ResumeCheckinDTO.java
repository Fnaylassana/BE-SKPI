package com.skpijtk.springboot_boilerplate.dto.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeCheckinDTO {
    private Long totalMahasiswa;
    private Long totalCheckin;
    private Long totalBelumCheckin;
    private Long totalTelatCheckin;
}
