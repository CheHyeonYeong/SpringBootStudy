package org.example.demo.service;

import lombok.extern.log4j.Log4j2;
import org.example.demo.dto.BoardDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest
@Log4j2
public class BoardServicetests {

    @Autowired
    private BoardService boardService;

    @Test
    public void resisterTest() {
        log.info("resisterTest");
        log.info(boardService.getClass().getName());
    }

    @Test
    public void saveTest() {
        BoardDTO boardDTO = BoardDTO.builder()
                .title("title..............")
                .content("content..........")
                .writer("user"+105050)
                .build();

        Long bno = boardService.register(boardDTO);
        log.info(bno);
    }

    @Test
    public void selectTest() {
        log.info("selectTest");
        BoardDTO boardDTO = boardService.readOne(101L);
        log.info(boardDTO);
    }

    @Test
    public void updateTest() {
        log.info("updateTest");
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("testupdate")
                .content("updatecontent101")
                .build();
        boardService.modify(boardDTO);
        log.info(boardService.readOne(101L));
    }

    @Test
    public void deleteTest() {
        log.info("deleteTest");
        Long bno = 101L;
        boardService.remove(bno);       //bno를 이미 지웠기 때문에 재차 remove 시 에러가 뜬다
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            boardService.readOne(bno);
        });
    }
}
