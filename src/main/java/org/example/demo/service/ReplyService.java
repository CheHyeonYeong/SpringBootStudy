package org.example.demo.service;

import org.example.demo.dto.BoardDTO;
import org.example.demo.dto.PageRequestDTO;
import org.example.demo.dto.PageResponseDTO;
import org.example.demo.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);
    void modify(ReplyDTO replyDTO);
    void remove(Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);

}
