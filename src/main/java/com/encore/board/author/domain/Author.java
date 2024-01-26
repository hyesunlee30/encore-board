package com.encore.board.author.domain;

import com.encore.board.author.dto.AuthorSaveReqDTO;
import com.encore.board.post.domain.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor // 리플렉션 왜 protected 인지 정리하기
@Getter
@Builder // 빌더
@AllArgsConstructor // 빌더
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

    //author를 조회할 때 post객체가 필요할 시에 선언
    //필요없으면 안 쓰는게 나은데 왜냐면
    //Author 객체를 json으로 직렬화 할 때
    //무한루프가 발생할 수 있다.
    //{"id":1, "name": "honggildong", "posts": [{"id":1, "title":"hello"}]}
    //AuthorResponseDTO 에서는 string으로만 풀어버리고 posts 를 꺼내서 넣어주면 된다. posts 객체 반환 X
    //{"name": "honggildong", "posts": [{"id":1, "title":"hello"}]}
    //직렬화 -> json
    //josn -> 객체 역직렬화
    //cascade = CascadeType.ALL 과 CascadeType.PERSIST 의 차이를 모르겠음
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //FK 주인 post 변수명 바뀌면 바꿔야함
    // cascade = CascadeType.PERSIST --> DB 차원이 아니라 프로그램 차원
    // PERSIST는 등록
    // author가 등록되자마자 post를 강제로 만들어 진다는 뜻
    //
    // post 테이블에서 적혀 있는 변수명
    // mappedBy = "author_id",
    // author 테이블 입장에서 post 테이블을 조회하려면 select * from post where author_id
    // mappedBy를 연관관계의 주인을 명시하고, fk를 관리하는 변수명을 명시, 핵심적인 위치에 있느냐
    private List<Post> posts;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;


    public void update(AuthorSaveReqDTO reqDTO) {
        this.name = reqDTO.getName();
        this.email = reqDTO.getEmail();
        this.password = reqDTO.getPassword();
        this.role = Objects.equals(reqDTO.getRole(), "관리자") ?Role.ADMIN:Role.USER;
    }
    public void updateAuthor(String name, String password) {
        this.name = name;
        this.password =password;

    }


    public void setPost(List<Post> posts) {
        this.posts = posts;
    }
}
