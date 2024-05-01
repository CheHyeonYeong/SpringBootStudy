package org.example.demo.repository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.example.demo.domain.Board;
import org.example.demo.domain.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.IntStream;
@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    //테스트 : 있는 게시글 중에 댓글 추가....80번에 댓글추가
    @Test
    public void testInsert() {
        Board board = Board.builder().bno(96L).build();
        IntStream.rangeClosed(1,100).forEach(i -> {
            Reply reply = Reply.builder()
                    .board(board)
                    .replyText("test")
                    .replyer("gusdud")
                    .build();
            replyRepository.save(reply);

        });
    }

    @Transactional      //lazy 하니가 출력 이후에 적용이 되나, transactional은 쿼리가 다 성공해야 성공처리를 하기 때문에 완료 이후에 가져오게 되어 성공함
    @Test
    public void testBoardReplies() {
        //실제 게시물 번호
        Long bno = 96L;
        Pageable pageable = PageRequest.of(0,10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfBoard(bno,pageable);

        result.getContent().forEach(reply -> {log.info(reply);});

    }



}
