package org.example.demo.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.demo.security.handler.Custom403Handler;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class CustomerSecurityConfig {

    //Remembet Me 서비스를 위한 변수들
    private final DataSource dataSource;                                    //서버 관련 정보 처리
    private final UserDetailsService userDetailsService;        //사용자관련 정보 처리

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("configure");

        //사용자 로그인 페이지
        http.formLogin(form -> {
            form.loginPage("/member/login"); //default login page가 아닌 다른 페이지로 바꿔준다
        });
        //csrf 설정
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        //remember-me 설정
        http.rememberMe(httpSecurityRememberMeConfigurer -> {
            httpSecurityRememberMeConfigurer.key("123456789")           //DB에 저장해서 작업할 수 있어야 remember 되기 때문이다.
                    .tokenRepository(persistentTokenRepository())
                    .userDetailsService(userDetailsService)
                    .tokenValiditySeconds(60*60*24*30);     //30일
        });

        //exception Handler 설정
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(acDeniedHandler());
        });

        http.oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
           httpSecurityOAuth2LoginConfigurer.loginPage("/member/login");
//           httpSecurityOAuth2LoginConfigurer.successHandler()
        });

        return http.build();
    }

    // 패스워드 암호화 처리하는 객체  -- 순환참조 문제로 인해서 다른 별개의 Configuration을 생성하여 처리...
    // 순환 구조의 발생 원인은 userDetailsService에서 의존성 주입을 한 PasswordEncoder를 설정한 Configuration에서
    // 다시 불러오는 구조가 되어 순환 구조가 됨.


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("web configure");
        //static 있는 곳은 무시하겠다는 코드
        return webSecurity -> webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    //패스워드 암호화 처리하는 객체
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    //PersistentTokenRepository
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);     //통신을 위함
        return repo;
    }
    //AccessDeniedHandler 빈 등록
    @Bean
    public AccessDeniedHandler acDeniedHandler() {
        return new Custom403Handler();
    }

}