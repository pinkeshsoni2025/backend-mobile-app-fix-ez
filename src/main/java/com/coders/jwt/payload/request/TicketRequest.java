package com.coders.jwt.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TicketRequest {
	@NotBlank
    private String title;
	@NotBlank
    private String description;
    @NotBlank
    private String category;
    @NotBlank
    private String priority;
}

