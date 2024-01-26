package com.encore.board.post.domain;

import com.encore.board.author.domain.Author;
import com.encore.board.post.dto.PostUpdateReqDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 3000, nullable = false)
    private String contents;
    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;
    //fk
    //name, db에 컬럼 이름을 뭐라고 해야할지 적어두는 것
    @JoinColumn(name = "author_id")
    //post 입장에서는 author가 다대일, 즉시로딩이 default FetchType.EAGER 그래서 FetchType.LAZY로 변경
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JoinColumn(nullable = false, name = "author_email", referencedColumnName = "email")
    private Author author; //@OneToMany(mappedBy = "author") // post 테이블에서 적혀 있는 변수명

    private String appointment;
    private LocalDateTime appointmentTime;

    public static Post updatePost(Post post, PostUpdateReqDTO dto) {
        post.contents = dto.getContents();
        post.title = dto.getTitle();
        return post;
    }

    public void updateAppointment(String appointment) {
        this.appointment = appointment;
    }

}
