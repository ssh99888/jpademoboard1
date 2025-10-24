package org.ssh.jpademo.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.ssh.jpademo.domain.Board;
import org.ssh.jpademo.dto.BoardDTO;
import org.ssh.jpademo.dto.PageRequestDTO;
import org.ssh.jpademo.dto.PageResponseDTO;

import java.util.List;

public interface BoardService {
    Long insertBoard(BoardDTO board);
    List<BoardDTO> findAllBoards();
    BoardDTO findBoardById(long bno, Integer mode);
    void updateBoard(BoardDTO boardDTO);
    void deleteBoardById(long bno);
    PageResponseDTO<BoardDTO> getPageList(PageRequestDTO pageRequestDTO);

    default Board dtoToEntity(BoardDTO boardDTO) {
        Board board = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .author(boardDTO.getAuthor())
                .build();
        return board;
    }
    default BoardDTO entityToDTO(Board board) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .readcount(board.getReadcount())
                .regDate(board.getRegDate())
                .updateDate(board.getUpdateDate())
                .build();
        return boardDTO;
    }
}
