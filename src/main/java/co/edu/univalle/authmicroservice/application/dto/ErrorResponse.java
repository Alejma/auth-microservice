package co.edu.univalle.authmicroservice.application.dto;

import java.util.List;

public record ErrorResponse(
        String error,
        String message,
        int status,
        List<String> details
) {
    public ErrorResponse(String error, String message, int status) {
        this(error, message, status, null);
    }
}