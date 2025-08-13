package com.skpijtk.springboot_boilerplate.dto.DTO;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PaginationDTO<T> {
    private List<T> data;
    private long totalData;
    private int totalPage;
    private int currentPage;
    private int pageSize;
}
