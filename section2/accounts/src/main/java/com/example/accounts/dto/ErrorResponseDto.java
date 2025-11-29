package com.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

// DTO for error responses
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
@Getter @Setter @NoArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "API path invoked by client"
    )
    private String path;

    @Schema(
            description = "HTTP status code of the error"
    )
    private HttpStatus status;

    @Schema(
            description = "Error message describing the issue"
    )
    private String message;

    @Schema(
            description = "Timestamp when the error occurred"
    )
    private LocalDateTime timestamp;
}
