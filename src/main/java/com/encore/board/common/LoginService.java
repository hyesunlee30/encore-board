package com.encore.board.common;

import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.author.service.AuthorService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//email/password 넣고 회원가입 => 암호화된 password가 DB 저장
//1) 인증불필요 url -> 바로 화면 return
//2) 인증필요 url => /author/login-page
//3) 로그인 - doLogin
//로그인 과정: DB에서 해당 사용자 조회 -> DB의 값과 화면에서 사용자가 입력값 비교
//DB에서 해당 사용자 조회 -- 이건 우리가 해야한다.
//스프링에서 암호화된 값과 DB에 암호화된 값이 같은지 확인.
//session은 저장공간


@Service
public class LoginService implements UserDetailsService {

    private final AuthorRepository repository;

    public LoginService(AuthorRepository repository) {
        this.repository = repository;
    }

    //로그인 과정: DB에서 해당 사용자 조회 -> DB의 값과 화면에서 사용자가 입력값 비교 이부분을 하겠다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = repository.findByEmail(username).get();
        //userEmail, userPassword, authoritics
        //상식
        //인증(authentication): 로그인 했냐
        //인가(authorization) : 권한이 있나
//        Role role = author.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();
        //도메인 단위 분류가 여러개
        authorities.add(new SimpleGrantedAuthority("ROLE_"+author.getRole()));

        //해당 메서드에서 return되는 User객체는 session은 session 저장소에 저장되어 계속 사용
        return new User(author.getEmail(), author.getPassword(), authorities);
    }
}
