package org.example.demo.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.demo.dto.MemberSecurityDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    //이후 Bean 등록하러감
    private final PasswordEncoder passwordEncoder;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //filterchain은 Security가 알아서 작업해주고 있다!
        log.info("onAuthenticationSuccess");
        log.info(authentication.getPrincipal());        //사용자 정보

        //request로 받아서 response로 보내고 authentication을 받는다 인증 정보는 spring Security에서 건내준다
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();

        String encodePw = memberSecurityDTO.getPassword();
        //소셜 로그인이고 회원 패스워드 1111 => 기본 값, 로그인 했을 때 
        if(memberSecurityDTO.isSocial() && (memberSecurityDTO.getMpw().equals("1111")  //혹시 encoding 값이 아닐까 걱정되어 유효성을 넣음
                || passwordEncoder.matches("1111", memberSecurityDTO.getMpw()))) {
            log.info("Should change password");
            log.info("Redirect to Member Modify");
            response.sendRedirect("/member/modify");
            return;
        } else {
            response.sendRedirect("/board/list");
        }
    }
    
}
