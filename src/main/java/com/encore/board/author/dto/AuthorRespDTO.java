package com.encore.board.author.dto;

import com.encore.board.author.domain.Author;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Member;
import java.time.LocalDateTime;

@Builder
@Getter
public class AuthorRespDTO {
    private Long id;
    @Setter
    private String name;
    private String email;
    @Setter
    private String password;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String role;
    private int post_cnt;

    public static AuthorRespDTO of (Author author) {
        AuthorRespDTO respDTO = AuthorRespDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .password(author.getPassword())
                .createdTime(author.getCreatedTime())
                .updatedTime(author.getUpdatedTime())
                .role(author.getRole()!=null?author.getRole().getLabel():null)
                .post_cnt(author.getPosts()!=null?author.getPosts().size():0)
                .build();
        return  respDTO;
    }
}
