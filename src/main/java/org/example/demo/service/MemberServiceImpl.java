package org.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.demo.domain.Member;
import org.example.demo.domain.MemberRole;
import org.example.demo.dto.MemberJoinDTO;
import org.example.demo.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException {
        String mid = memberJoinDTO.getMid();
        //중복확인
        boolean exist = memberRepository.existsById(mid);
        if (exist) {
            throw new MidExistException();
        }
        //modelMapper로 memberJoinDTO에 있는 값을 member 클래스로 변환
        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.chagePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));       //password는 암호화
        member.addRole(MemberRole.USER);        //권한부여

        //결과 확인
        log.info("=================");
        log.info(member);
        log.info(member.getRoleSet());
        //db 저장
        memberRepository.save(member);
    }
}
