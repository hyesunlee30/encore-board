package com.encore.board.post.dto;

import com.encore.board.post.domain.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class PostListResDTO {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String author_email;

    public static PostListResDTO of(Post post) {
        PostListResDTO dto = PostListResDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .createdTime(post.getCreatedTime())
                .updatedTime(post.getUpdatedTime())
                .author_email(post.getAuthor()==null?"익명유저":post.getAuthor().getEmail())
                .build();
        return  dto;
    }
}
