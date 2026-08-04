package com.board.board.service;

import com.board.board.dto.Board;
import com.board.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    // 전체 조회
    public List<Board> listAll() {
        return boardMapper.listAll();
    }

    // 글쓰기
    public void boardWrite(Board board) {
        boardMapper.boardWrite(board);
    }
}
