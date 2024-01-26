package com.encore.board.author.repository;

import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;



//DataJpaTest 어노테이션을 사용하면 매 테스트가 끝날때마다 DB 원상복구
// 모든 spring bean을 생성하지 않고, DB테스트 특화 어노테이션
// @DataJpaTest
//SpringBootTest어노테이션은 자동롤백기능을 지원하지 않고, 별도로 롤백 코드 또는 어노테이션 필요
//SpringBootTest어노테이션은 실제 스프링 실행과 동일하게 스프링빈생성 및 주입
@DataJpaTest
// @SpringBootTest
//@Transactional
//replace = AutoConfigureTestDatabase.Replace.NONE
//DataJpaTest와 함께 쓸 때, Any를 쓰면 어떤 걸 찾아다 주냐면
//.ANY 를 쓰면 인메모리 DB를 찾으러 가기 때문에 Replace.NONE 쓰면 우리가 쓰는 DB 쓰게 된다.
//replace = AutoConfigureTestDatabase.Replace.ANY 가 디폴트
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//ActiveProfiles("test") //application-test.yml 파일을 찾아 설정값 세팅
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void authorSaveTest() {
        Author author = Author.builder()
                .name("test")
                .role(Role.USER)
                .email("ww15")
                .password("123")
                .build();
        Author author1 =authorRepository.save(author);

        Assertions.assertEquals(author1.getEmail(), author.getEmail());



    }
}
