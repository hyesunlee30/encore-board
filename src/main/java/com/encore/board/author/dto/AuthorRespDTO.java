package com.encore.board.author.dto;

import com.encore.board.author.domain.Author;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Member;
import java.time.LocalDateTime;

@Builder
@Getter
public class AuthorRespDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public static AuthorRespDTO of(Author author) {
        AuthorRespDTO respDTO = AuthorRespDTO.builder().
                id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .password(author.getPassword())
                .createdTime(author.getCreatedTime())
                .updatedTime(author.getUpdatedTime())
                .build();
        return  respDTO;
    }
}
