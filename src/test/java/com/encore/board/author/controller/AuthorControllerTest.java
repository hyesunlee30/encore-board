package com.encore.board.author.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.encore.board.author.dto.AuthorRespDTO;
import com.encore.board.author.service.AuthorService;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


//WebMvcTest를 이용해서 Controller계층을 테스트, 모든 스프링빈을 생성하고 주입하지는 않음.
//redis bean 객체 주입 안했잖아, 어떤 been 주입 안 했잖아 이런 일이 발생하면서
//성능이 안 나온다.
//@WebMvcTest(AuthorController.class)
//--- 위에 하나쓰던가 아래 두개 쓰던가
//--- 밑에 두개 쎄트
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {
    //싱글톤으로 만들어진 객체 주입받은 것이다.
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void LogTest() {

    }

//    @Test
//    //@WithMockUser
//    void authorFindByid() throws Exception {
//
//        AuthorRespDTO authorRespDTO = AuthorRespDTO.builder()
//                .id(1L)
//                .name("name")
//                .email("naver")
//                .password("123")
//                .build();
//
//
//        Mockito.when(authorService.findById(1L)).thenReturn(authorRespDTO);
//
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/author/1/circle/entity"))
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.name").value("name"));
//
//
//    }
}
