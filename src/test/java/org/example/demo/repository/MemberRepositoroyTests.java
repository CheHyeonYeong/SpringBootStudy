package org.example.demo.repository;

import lombok.extern.log4j.Log4j2;
import org.example.demo.domain.Member;
import org.example.demo.domain.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class MemberRepositoroyTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMemberTest() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .mid("user"+i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email"+i+"@test.com")
                    .build();
            member.addRole(MemberRole.USER);    //권한 설정 : 현재 권한은 admin과 user 밖에 없다

            if(i>=90){
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }

    @Test
    public void testRead(){
        Optional<Member> member = memberRepository.getWithRoles("user99");
        Member member1 = member.orElseThrow();

        log.info(member1);
        log.info(member1.getRoleSet());

        member1.getRoleSet().forEach(memberRole -> {log.info(memberRole.name());});
    }

    @Test
    public void testFindByEmail(){
        Optional<Member> member = memberRepository.findByEmail("email1@test.com");
        Member member1 = member.orElseThrow();
        log.info(member1);
        log.info(member1.getRoleSet());
        member1.getRoleSet().forEach(memberRole -> {log.info(memberRole.name());});

    }
}
