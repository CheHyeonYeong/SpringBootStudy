package org.example.demo.service;

import lombok.extern.log4j.Log4j2;
import org.example.demo.dto.BoardDTO;
import org.example.demo.dto.PageRequestDTO;
import org.example.demo.dto.PageResponseDTO;
import org.example.demo.dto.ReplyDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {


    @Autowired
    private ReplyService replyService;

    @Test
    public void resisterTest() {
        log.info("resisterTest");
        log.info(replyService.getClass().getName());

        ReplyDTO replyDTO = ReplyDTO.builder()
                .bno(104L)
                .replyText("replyText")
                .replyer("O 형")
                .build();

        Long rno = replyService.register(replyDTO);
        log.info(rno);
    }

    @Test
    public void selectTest() {
        log.info("selectTest");
        ReplyDTO replyDTO = replyService.read(96L);
        log.info(replyDTO);
    }


    @Test
    public void updateTest() {
        log.info("updateTest");
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(102L)
                .bno(104L)
                .replyText("replyTextOnemoreTime")
                .replyer("O 형")
                .build();
        replyService.modify(replyDTO);
        log.info(replyService.read(96L));
    }

    @Test
    public void deleteTest() {
        log.info("deleteTest");
        Long rno = 101L;
        replyService.remove(rno);       //bno를 이미 지웠기 때문에 재차 remove 시 에러가 뜬다
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            replyService.read(rno);
        });
    }

    @Test
    public void testGetListOfBoard() {
        Long bno = 104L;
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageResponseDTO result = replyService.getListOfBoard(bno, pageRequestDTO);
        result.getDtoList().forEach(e-> {
            log.info(e);
        });
    }

    @Test
    public void testDelete() {
        Long rno = 1000L;
        replyService.remove(rno);
    }
}
