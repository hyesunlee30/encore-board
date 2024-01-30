package com.encore.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity// 해당 어노테이션은 spring security설정을 커스터마이징 하기 위함
@EnableGlobalMethodSecurity(prePostEnabled = true) //pre 사전, post 사후 인증권한검사 어노테이션
//webSecurityConfig 어쩌구는 이제 사용 안 함
public class SecurityConfig {

    //기존 설정 무시하고 우리가 만든 filter 탐
    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //csrf 보안 공격에 대한 설정은 하지 않겠다라는 의미
                .csrf().disable()
                //특정 url대해서는 인증처리 하지 않고, 특정 url대해서는 인즈처리 인증처리 하겠다라는 설정
                .authorizeRequests()
                    .antMatchers("/","/author/create","/author/login-page")
                        .permitAll()
                //그 외 요청은 모두 인증필요
                .anyRequest().authenticated()
                .and()
//                //세션을 사용 하지 않을 거면 주석 풀어서 사용
//                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                    .loginPage("/author/login-page")
                    //.loginPage("/login")
//                    //스프링 내장메서드를 사용하기 위해 /doLogin url 사용
                    //.loginProcessingUrl("/author/list")
                    .loginProcessingUrl("/doLogin")
                        .usernameParameter("email")
                        .passwordParameter("pw")
                //직접만든 LoginSuccessHandler
                .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
                    .logoutUrl("/doLogout")
                .and()
                .build();
    }
}
