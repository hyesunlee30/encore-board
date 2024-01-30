package com.encore.board.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//로그인이 성공했을때 실행할 로직 정의
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //사용자의 객체에서
        //session을 꺼내면 jsession id를
        //세션저장소
        HttpSession httpSession = request.getSession();

        //email 정보
        //authentication 객체 안에는 User객체가 들어가 있고, 여기서 getName은 email을 의미
        httpSession.setAttribute("email", authentication.getName());

        response.sendRedirect("/");
    }
}
