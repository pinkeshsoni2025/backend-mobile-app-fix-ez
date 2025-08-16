package com.coders.jwt.payload.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String message;
    private String description;
    private LocalDateTime created_at;
    private String created_by;
    private String category;
    private Long ticket_id;
}
