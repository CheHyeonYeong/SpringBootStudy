package org.example.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.demo.domain.Board;
import org.example.demo.dto.BoardDTO;
import org.example.demo.dto.BoardListReplyCountDTO;
import org.example.demo.dto.PageRequestDTO;
import org.example.demo.dto.PageResponseDTO;
import org.example.demo.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor //생성자 주입
@Transactional          //서비스가 참조될 때 동시 참조가 될 때 처리해 줌
//transaction은 db에 여러 작업을 해야하는 경우 완전 성공 시 처리, 실패시 되돌리기 됨
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;      //jpa를 불러와야 하므로 repository를 불러와야 한다. 생성자 주입하려면 (바로 문제를 파악함) final로 만들어야 한다 -> autowired써도됨
    private final ModelMapper modelMapper;              //등록을 위해서 넣어줘야 한다!!! 생성자 주입은 똑같이 이뤄진다.

    @Override
    public Long register(BoardDTO boardDTO){
        Board board = modelMapper.map(boardDTO, Board.class);       //entity를 dto로 만든다
        long bno  = boardRepository.save(board).getBno();           //저장하고 나서 저장되어 있는 persistence에 등록되어 있는 bno를 불러오게 된다
        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();         //exception 처리
        BoardDTO boardDTO =  modelMapper.map(board, BoardDTO.class);
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {

//        Board board = modelMapper.map(boardDTO, Board.class);
//        boardRepository.save(board);

        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow();     //만일 없다고 나오면 => 내가 선택한 게시글은 없다고 뜸

        board.change(boardDTO.getTitle(), boardDTO.getContent());       //persistance에 있는 내용이 변경됨
        boardRepository.save(board);            //등록 되어 있기 때문
    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageabble("bno"); //게시글 번호를 기준으로 정렬
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        //변환 .... Board -> BoardDTO
        List<BoardDTO> dtoList = result.stream().map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()      //boardDTO 의 자식 객체까지 상관 없다
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageabble("bno"); //게시글 번호를 기준으로 정렬
        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }
}
