package org.example.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.demo.dto.MemberJoinDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public void loginGET(String error, String logout){
        //login 친구가 error 메세지를 보내기 때문
        log.info("login get...............");
        log.info("logout : " + logout);
        if(logout != null){
            log.info("user logout..........");
        }
    }

    @GetMapping("/join")
    public void joinGET(){
        log.info("join get...............");
    }

    @PostMapping("/join")
    public String joinPOST(MemberJoinDTO memberJoinDTO){
        log.info("join get...............");
        log.info("memberJoinDTO : " + memberJoinDTO);

        return "redirect:/board/list";
    }
}
