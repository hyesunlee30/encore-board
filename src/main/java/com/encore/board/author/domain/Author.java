package com.encore.board.author.domain;

import com.encore.board.author.dto.AuthorSaveReqDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder // 빌더
@AllArgsConstructor // 빌더
@NoArgsConstructor // 리플렉션
@Entity // db
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)

    private String name;

    @Column(length = 20, unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    public Author update(Author author, AuthorSaveReqDTO reqDTO) {

        author.name = reqDTO.getName();
        author.email = reqDTO.getEmail();
        author.password = reqDTO.getEmail();
        author.role = Objects.equals(reqDTO.getRole(), "관리자") ?Role.ADMIN:Role.USER;
        return author;
    }
}
