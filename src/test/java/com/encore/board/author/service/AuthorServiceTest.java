package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.AuthorRespDTO;
import com.encore.board.author.dto.AuthorSaveReqDTO;
import com.encore.board.author.repository.AuthorRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void findAllTest() {
        List<Author> authors = new ArrayList<>();
//        Author author = Author.builder()
//                .email("emil")
//                .name("name")
//                .role(Role.USER)
//                .build();
//        Author author1 = Author.builder()
//                .email("emil2")
//                .name("name2")
//                .role(Role.USER)
//                .build();
//        authors.add(author);
//        authors.add(author1);

        authors.add(new Author());
        authors.add(new Author());

        //Mock repository 기능 구현
        Mockito.when(authorRepository.findAll()).thenReturn(authors);

        Assertions.assertEquals(2, authorService.findAll().size());



    }

    @Test
    void updateTest() {
        Long authorId = 1L;
        Author author = Author.builder()
                .id(authorId)
                .email("emil")
                .name("name")
                .password("1234")
                .build();




        //Mock repository 기능 구현
        //findById 를 하면 지정된 값으로 반납하도록 되어 있음.
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        //바꿀값
        AuthorSaveReqDTO authorRespDTO = new AuthorSaveReqDTO();
        authorRespDTO.setName("test");
        authorRespDTO.setEmail("emil1");

        //update 1L 으로
        AuthorRespDTO new_dto = authorService.update(authorId, authorRespDTO);
        Assertions.assertEquals(authorRespDTO.getEmail(), new_dto.getEmail());

    }

}
