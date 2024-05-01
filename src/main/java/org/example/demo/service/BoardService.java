package org.example.demo.service;

import org.example.demo.dto.BoardDTO;
import org.example.demo.dto.BoardListReplyCountDTO;
import org.example.demo.dto.PageRequestDTO;
import org.example.demo.dto.PageResponseDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO);
    BoardDTO readOne(Long bno);
    void modify(BoardDTO boardDTO);
    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    //댓글 숫자까지 처리
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

}
