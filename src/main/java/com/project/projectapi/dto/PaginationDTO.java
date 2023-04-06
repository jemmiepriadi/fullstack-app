package com.project.projectapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PaginationDTO <T>{
    @Getter @Setter
    private long totalPages;

    @Getter @Setter
    private List<T> data;

    @Getter @Setter
    private int number;

    @Getter @Setter
    private long totalElements;
}
