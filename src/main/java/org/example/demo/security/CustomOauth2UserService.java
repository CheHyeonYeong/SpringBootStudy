package org.example.demo.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.demo.domain.Member;
import org.example.demo.domain.MemberRole;
import org.example.demo.dto.MemberSecurityDTO;
import org.example.demo.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    //솟ㄹ에 등록된 이메일을 통해서 사용자 구분을 하나 없는 경우 멤버 추가 설정을 위해 DI
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info(userRequest);
        log.info("oauth user================");
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName=clientRegistration.getClientName();
        log.info("Name : "+clientName);     // 어떤 소셜을 사용했니?

        //https://kapi.kakao.com/v2/user/me 여기 결과값이 들어간다
        OAuth2User oAuth2User = super.loadUser(userRequest);        //마지막 인증정보가 userRequest에 들어간다

        Map<String,Object> paramMap = oAuth2User.getAttributes();
//        paramMap.forEach((k,v)->{
//           log.info("--------------------------------------");
//           log.info(k+" : "+v);
//        });

        String email = null;
        switch (clientName){        //다 email을 보내는 작업이 다르기 때문에 각자의 사정에 맞춰서 넣어주자
            case "kakao":
                email = getKakaoEmail(paramMap);
                log.info("email : "+email);
                break;
            case "google":
                break;
            default:
                break;
        }

        return generateDTO(email,paramMap);      //결과값 불러와서 작업하면 됨~
    }
    private String getKakaoEmail(Map<String,Object> paramMap){
        Object value = paramMap.get("kakao_account");
        LinkedHashMap accountMap = (LinkedHashMap)value;
        String email = (String)accountMap.get("email");
        return email;
    }

    private MemberSecurityDTO generateDTO(String email, Map<String,Object> paramMap){
        Optional<Member> result = memberRepository.findByEmail(email);

        //만일 데베에 해당 이메일 사용자가 없는 경우
        if(result.isEmpty()){
            // 회원 추가... mid는 이메일 주소, 패스워드는 1111
            Member member = Member.builder()
                    .mid(email)
                    .mpw(passwordEncoder.encode("1111"))
                    .email(email)
                    .social(true)
                    .build();
            member.addRole(MemberRole.USER);
            memberRepository.save(member);
            //MemberSecurityDTO 로 반환
            MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(email, "1111", email, false, true,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            memberSecurityDTO.setProps(paramMap);
            return memberSecurityDTO;
        }else{
            Member member = result.get();
            MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(member.getMid(), member.getMpw(), member.getEmail(), member.isDel(), member.isSocial(),
                    member.getRoleSet().stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toList()));
            return memberSecurityDTO;
        }

    }
}
