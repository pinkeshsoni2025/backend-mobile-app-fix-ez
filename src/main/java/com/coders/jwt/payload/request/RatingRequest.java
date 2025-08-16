package com.coders.jwt.payload.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RatingRequest {

    @NotBlank
    private Long id;
    
    @NotBlank
    private String comment;
    
    @NotBlank
    private Integer rating;
    
    @NotBlank
    private LocalDateTime createdAt;
    
    @NotBlank
    private String createdBy;
    
    @NotBlank
    private Long ticketId;
}

