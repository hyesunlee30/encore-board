package com.encore.board.author.controller;

import com.encore.board.author.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
//Slf4j 어노테이션(롬복)을 통해 쉽게 logback 로그라이브러리 사용 가능
@Slf4j
public class TestController {

    //lombok 어노테이션 안 쓰고 선언해서 사용할 수도 있음.
//    private static final Logger log = LoggerFactory.getLogger(LogTestController.class);

    private final AuthorService authorService;
    @GetMapping("log/test1")
    public String testMethod1() {
        log.debug("debug 로그입니다");
        log.error("error 로그입니다");
        log.info("info 로그입니다");

        return "ok";
    }
    @GetMapping("exception/test1/{id}")
    public String notMethod1(@PathVariable Long id) {
        authorService.findById(id);
        return "OK";
    }

    @GetMapping("exception/test2/{id}")
    public String illiger1(@PathVariable Long id) {
        authorService.findById(id);
        return "OK";
    }

}
