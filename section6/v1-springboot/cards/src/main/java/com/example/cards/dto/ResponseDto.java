package com.example.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ResponseDto {

    @Schema(
            description = "Status code of the response"
    )
    private String statusCode;

    @Schema(
            description = "Status message of the response"
    )
    private String statusMsg;
}
