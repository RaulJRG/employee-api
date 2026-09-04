package com.raulrobles.employeeapi.exception;

import java.time.LocalDateTime;

public record ApiError(
    LocalDateTime timestamp,
    int statusCode,
    String error,
    String message,
    String path
) {}
