package org.example.demo.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.demo.dto.BoardDTO;
import org.example.demo.dto.BoardListReplyCountDTO;
import org.example.demo.dto.PageRequestDTO;
import org.example.demo.dto.PageResponseDTO;
import org.example.demo.service.BoardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
//        PageResponseDTO<BoardDTO> responceDTO = boardService.list(pageRequestDTO);
        PageResponseDTO<BoardListReplyCountDTO> responceDTO = boardService.listWithReplyCount(pageRequestDTO);
        log.info(responceDTO);
        model.addAttribute("responceDTO",responceDTO);

    }

    @PreAuthorize("hasRole('USER')")    //사전 인가 체크 => Role_User와 같은 의미..로 특정 권한 사용자만 접근 가능하도록 설정
    @GetMapping("/register")
    public void registerGet(Model model) {

    }

    @PreAuthorize("isAuthenticated()")  //인증된 사용자만 접근 가능...
    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("board Post register commin");
        
        //값 검증 이후 확인
        if(bindingResult.hasErrors()) {     //검증시 에러가 있는 경우
            log.error("has Error");
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            return "redirect:/board/register";
        }
        log.info("board Post register"+boardDTO);

        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result",bno);     //그 화면에만 넘기고 보기만 하면 되니까 flash로 만드는 편을 추천한다.
        //bno 가 들어왔는지 확인하려고 할 뿐이라서 따로 안 쓸 것 같다~

        return "redirect:/board/list";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {
        BoardDTO boardDTO = boardService.readOne(bno);
        log.info(boardDTO);
        model.addAttribute("dto",boardDTO);
    }

    @PreAuthorize("principal.username == #boardDTO.writer")
    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("board Modify commin");
        if(bindingResult.hasErrors()) {
            log.error("has Error");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno",boardDTO.getBno());
            return "redirect:/board/modify?"+link;
        }
        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result","modify success");
        redirectAttributes.addAttribute("bno",boardDTO.getBno()); //이렇게 하면 뒤에 붙는다.

        return "redirect:/board/read";
    }

    @PreAuthorize("principal.username == #boardDTO.writer")
    @PostMapping("/remove")
    public String remove(BoardDTO boardDTO,PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {
        log.info("remove : " + boardDTO.getBno());
        boardService.remove(boardDTO.getBno());
        //삭제시에는 페이지 번호를 1로, 사이즈는 전달 받는다.
        redirectAttributes.addAttribute("page", 1);
        redirectAttributes.addAttribute("size", pageRequestDTO.getSize());
        return "redirect:/board/list";
    }
}
