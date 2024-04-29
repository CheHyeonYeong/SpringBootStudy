package org.example.demo.service;

import lombok.extern.log4j.Log4j2;
import org.example.demo.dto.BoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        BoardDTO boardDTO = boardService.readOne(1L);
        log.info(boardDTO);

    }
}
