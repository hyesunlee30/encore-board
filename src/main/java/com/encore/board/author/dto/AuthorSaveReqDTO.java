package com.encore.board.author.dto;

import lombok.Data;

@Data
public class AuthorSaveReqDTO {
    private String name;
    private String email;
    private String pw;
    private String role;
}
