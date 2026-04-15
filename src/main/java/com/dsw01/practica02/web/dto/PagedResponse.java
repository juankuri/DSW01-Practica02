package com.dsw01.practica02.web.dto;

import java.util.List;

public record PagedResponse<T>(
        List<T> content,
        int number,
        int size,
        long totalElements,
        int totalPages) {
}
