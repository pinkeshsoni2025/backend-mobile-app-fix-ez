package com.coders.jwt.payload.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingResponse {
    private Long id;
    private String comment;
    private Integer rating;
    private LocalDateTime createdAt;
    private String createdBy;
    private Long ticketId;
}
