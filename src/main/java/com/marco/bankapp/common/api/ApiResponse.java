package com.marco.bankapp.common.api;

import java.time.Instant;

public record ApiResponse<T>(
        Instant timestamp,
        T data
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(Instant.now(), data);
    }
}