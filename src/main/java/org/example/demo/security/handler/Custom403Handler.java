package org.example.demo.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Log4j2
public class Custom403Handler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("===========access denied===========");
        response.setStatus(HttpStatus.FORBIDDEN.value());   //403 에러 띄워주세요~
        //json 요청이 유효한지 확인
        String contentType = request.getHeader("Content-Type");
        boolean jsonRequest = contentType.startsWith("application/json");       //시작 문자열이 json인지 확인

        log.info("isJson : "+ jsonRequest);

        //일반 request인 경우
        if(!jsonRequest) {
            response.sendRedirect("/member/login?error=ACCESS_DENIED");     //로그인 창이 띄워졌던 것처럼 창을 띄워준다.
        }
    }
}
