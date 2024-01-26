package com.encore.board.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostSaveReqDTO {
    private String author_email;
    private String title;
    private String contents;
    private String appointment;
    private String appointmentTime;
}
