package org.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.demo.domain.Reply;
import org.example.demo.dto.PageRequestDTO;
import org.example.demo.dto.PageResponseDTO;
import org.example.demo.dto.ReplyDTO;
import org.example.demo.repository.ReplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = modelMapper.map(replyDTO, Reply.class);
        reply.setBoard(replyDTO.getBno());
        Long rno = replyRepository.save(reply).getRno();

        return rno;
    }

    @Override
    public ReplyDTO read(Long rno) {
        Optional<Reply> result = replyRepository.findById(rno);
        Reply reply = result.orElseThrow();         //exception 처리
        ReplyDTO replyDTO =  modelMapper.map(reply, ReplyDTO.class);
        replyDTO.setBno(reply.getBoard().getBno());

        return replyDTO;

    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Optional<Reply> result = replyRepository.findById(replyDTO.getRno());
        Reply reply = result.orElseThrow();         //exception 처리

        reply.changeText(replyDTO.getReplyText());       //persistance에 있는 내용이 변경됨
        replyRepository.save(reply);            //등록 되어 있기 때문
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }


    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = (Pageable) PageRequest.of(
                pageRequestDTO.getPage() <=0 ? 0 : pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        List<ReplyDTO> dtoList = result.getContent().stream().map(reply -> {
            ReplyDTO replyDTO = modelMapper.map(reply,ReplyDTO.class);
            replyDTO.setBno(reply.getBoard().getBno());
            return replyDTO;
        }).collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll().pageRequestDTO(pageRequestDTO).dtoList(dtoList).total((int)result.getTotalElements()).build();
    }
}
