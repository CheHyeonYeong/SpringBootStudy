package org.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.demo.dto.PageRequestDTO;
import org.example.demo.dto.PageResponseDTO;
import org.example.demo.dto.ReplyDTO;
import org.example.demo.service.ReplyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    @Operation(summary = "Replies Post -  Post 방식으로 댓글 등록")     //설명하는 어노테이션
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)            //받은 데이터 타입을 어떻게 처리하겠다, json 타입이 아니라면 처리를 못한다.
    public Map<String,Long> register(@Valid @RequestBody ReplyDTO replyDTO,
                                     BindingResult bindingResult) throws BindException{   //응답 객체를 생성한다. 현재 replydto로 받아서 replydto로 보낼 것이다
        log.info(replyDTO);
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);     //강제로 예외를 발생시켜서 넣어버림 
        }

        Map<String,Long> resultMap = new HashMap<>();       //key value 형식으로 데이터 전달, -> json 형식
        Long rno = replyService.register(replyDTO);         //받은 객체 정보를 rno라고 하는 받아 등록하고
        resultMap.put("rno",rno);                           //생성된 reply 형태를 등록하겠다
        return resultMap;        //확인용
    }


    //특정 게시물의 댓글 목록 보기
    //uri는 /replies/list/{bno}, get 방식
//    PathVariable 어노테이션 사용!! => 경로의 변수로 사용하겠다
    @Operation(summary = "Replies of Board로 겟 방식으로 특정 게시물 댓글 목록 처리...")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno, PageRequestDTO pageRequestDTO){
        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(bno, pageRequestDTO);
        return responseDTO;
    }

    @Operation(summary = "Read Reply - Get 방식으로 댓글 조회")
    @GetMapping("/{rno}")
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno){
        ReplyDTO replyDTO = replyService.read(rno);
        return replyDTO;

    }

    @Operation(summary = "Delete Reply - use Delete method ")
    @DeleteMapping("/{rno}")
    public Map<String, Long> deleteReply(@PathVariable("rno") Long rno){
        replyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno",rno);
        return resultMap;
    }

    @Operation(summary = "Modify Reply - use Put method ")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)      //json 방식으로 값이 들어올 때 쓴다
    public Map<String, Long> modify(@PathVariable("rno") Long rno, @RequestBody ReplyDTO replyDTO){
        replyDTO.setRno(rno);       //번호 일치를 위함
        replyService.modify(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno",rno);
        return resultMap;
    }

}
