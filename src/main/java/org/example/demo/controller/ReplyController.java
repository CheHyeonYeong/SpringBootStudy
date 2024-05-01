package org.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.example.demo.dto.ReplyDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/replies")
public class ReplyController {

    @Operation(summary = "Replies Post -  Post 방식으로 댓글 등록")     //설명하는 어노테이션
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)            //받은 데이터 타입을 어떻게 처리하겠다, json 타입이 아니라면 처리를 못한다.
    public Map<String,Long> register(@Valid @RequestBody ReplyDTO replyDTO,
                                     BindingResult bindingResult) throws BindException{   //응답 객체를 생성한다. 현재 replydto로 받아서 replydto로 보낼 것이다
        log.info(replyDTO);
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);     //강제로 예외를 발생시켜서 넣어버림 
        }

        Map<String,Long> resultMap = Map.of("rno",111L);
        return resultMap;        //확인용
    }




}
