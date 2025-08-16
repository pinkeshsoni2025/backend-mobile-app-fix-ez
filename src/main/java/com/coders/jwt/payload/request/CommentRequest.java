package com.coders.jwt.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {
	@NotBlank
    private Long id;
	@NotBlank
    private String messgage;
    @NotBlank
    private String created_at;
    @NotBlank
    private String created_by;
    @NotBlank
    private Long ticket_id;
}

