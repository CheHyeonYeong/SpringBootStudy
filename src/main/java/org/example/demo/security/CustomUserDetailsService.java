package org.example.demo.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.demo.domain.Member;
import org.example.demo.dto.MemberSecurityDTO;
import org.example.demo.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername : "+username);

        //DB에 등록된 사용자 정보를 불러옴....
        Optional<Member> result = memberRepository.getWithRoles(username);

        //UserDetails 객체로 반환하는 userDetails를 생성... => 이전 방식, 이후에는 UserDetails의 구현체인 User을 상속받은 DTO로 만들어서 넘긴다
//        UserDetails userDetails = User.builder().username("user1")
//                .password(passwordEncoder.encode("1111"))
//                .authorities("ROLE_USER")
//                .build();

        if(result.isEmpty()){throw new UsernameNotFoundException(username);}
        Member member = result.get();
        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(member.getMid(), member.getMpw(), member.getEmail(), member.isDel(), false,
                member.getRoleSet().stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toList())
        );

        return memberSecurityDTO;
    }
}