package org.ssh.jpademo.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ssh.jpademo.domain.Board;
import org.ssh.jpademo.dto.BoardDTO;
import org.ssh.jpademo.dto.PageRequestDTO;
import org.ssh.jpademo.dto.PageResponseDTO;
import org.ssh.jpademo.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Long insertBoard(BoardDTO boardDTO) {
        Board board = dtoToEntity(boardDTO);
        Long bno = boardRepository.save(board).getBno();
        return bno;
    }

    @Override
    public List<BoardDTO> findAllBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDTO> dtos = new ArrayList<>();
        for (Board board : boards) {
            dtos.add(entityToDTO(board));
        }
        return dtos;
    }

    @Override
    public BoardDTO findBoardById(long bno, Integer mode) {
        Board board = boardRepository.findById(bno).orElse(null);  // Optional<Board>
        if(mode == 1) {
            board.updateReadcount();
            boardRepository.save(board);
        }
//        BoardDTO boardDTO = entityToDTO(board);
        return entityToDTO(board);
    }

    @Override
    public void updateBoard(BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardDTO.getBno()).orElse(null);
        board.change(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);
    }

    @Override
    public void deleteBoardById(long bno) {
        Board board = boardRepository.findById(bno).orElse(null);
        boardRepository.delete(board);
    }

    @Override
    public PageResponseDTO<BoardDTO> getPageList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("bno");
        //Page<Board> result = boardRepository.findAll(pageable);  // 페이징정보, 보드정보
        //Page<Board> result = boardRepository.findKeyword(pageRequestDTO.getKeyword(), pageable);
        Page<Board> result = boardRepository.searchAll(
                pageRequestDTO.getTypes(),
                pageRequestDTO.getKeyword(),
                pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> entityToDTO(board))
                .collect(Collectors.toList());
        int total = (int)result.getTotalElements();

        PageResponseDTO<BoardDTO> responseDTO = PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();

        return responseDTO;
    }

}
