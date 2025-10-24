package org.ssh.jpademo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ssh.jpademo.domain.Board;
import org.ssh.jpademo.dto.BoardDTO;
import org.ssh.jpademo.dto.PageRequestDTO;
import org.ssh.jpademo.dto.PageResponseDTO;
import org.ssh.jpademo.service.BoardService;

@Controller
@Log4j2
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping("register")
    public void registerGet() {
        log.info("registerGet");
    }
    @PostMapping("register")
    public String registerPost(BoardDTO boardDTO) {
        log.info("registerPost");
        Long bno = boardService.insertBoard(boardDTO);
        log.info("board insert success: bno = " + bno);
        return "redirect:/board/list";
    }

    @GetMapping("list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.getPageList(pageRequestDTO);
        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
    }
//    @GetMapping("list")
    public void list(Model model) {
        log.info("list");
        model.addAttribute("boards", boardService.findAllBoards());
    }

    @GetMapping({"read","modify"})
    public void view_modify(@RequestParam("bno")long bno, Integer mode, PageRequestDTO pageRequestDTO, Model model) {
        log.info("view_modify");
        model.addAttribute("board", boardService.findBoardById(bno, mode));
    }
    @PostMapping("modify")
    public String modify(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        log.info("modify"+boardDTO);
        boardService.updateBoard(boardDTO);
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        redirectAttributes.addAttribute("mode", 1);
        return "redirect:/board/read";
    }

    @GetMapping("delete")
    public String delete(long bno) {
        log.info("delete");
        boardService.deleteBoardById(bno);
        return "redirect:/board/list";
    }

}
