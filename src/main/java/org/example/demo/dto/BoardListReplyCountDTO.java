package org.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {
    //특정 게시물의 숫자와 내용들을 다 가져올 수 없고, model로 넘어가기 때문에 값을 직접적으로 넘길 수 없다!
    //관련 정보를 넘겨줘야 한다

    //게시글 목록, 즉 list에서 사용하는 값
    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;

    private Long replyCount;

}
