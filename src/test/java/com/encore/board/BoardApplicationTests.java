package com.encore.board;

import com.encore.board.author.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BoardApplicationTests {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Test
	void contextLoads() {
	}
	@Test
	@DisplayName("패스워드 암호화 테스트")
	void passwordEncode() {
		// given
		String rawPassword = "1234";

		// when
		String encodedPassword = passwordEncoder.encode(rawPassword);
		System.out.println(encodedPassword);
	}

}
