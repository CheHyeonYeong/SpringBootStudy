package org.example.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.demo.domain.Board;
import org.example.demo.dto.BoardDTO;
import org.example.demo.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<Board> board = boardRepository.findById(bno);
        BoardDTO boardDTO =  modelMapper.map(board, BoardDTO.class);
        return boardDTO;
    }
}
