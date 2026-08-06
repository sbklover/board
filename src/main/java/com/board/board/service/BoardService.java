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

    // 한 페이지에 보여줄 게시글 수
    private static final int PAGE_SIZE = 7;

    // 글쓰기
    public void InsertBoard(Board board) {
        boardMapper.InsertBoard(board);
    }

    // 글 수정
    public void boardModify(Board board) {
        boardMapper.boardModify(board);
    }

    // 글 삭제
    public void boardDelete(Long no) {
        boardMapper.boardDelete(no);
    }

    // 단건 조회 (수정 폼 진입 시 기존 데이터 조회)
    public Board boardView(Long no) {
        return boardMapper.boardView(no);
    }

    // 전체 조회 - 관리자 페이지용
    public List<Board> listAll() {
        return boardMapper.listAll();
    }

    // 페이징된 목록 조회 (page는 0부터 시작) - 관리자 페이지용
    public List<Board> listPaged(int page) {
        int offset = page * PAGE_SIZE;
        return boardMapper.boardListPaged(offset, PAGE_SIZE);
    }

    // 전체 페이지 수 계산 - 관리자 페이지용
    public int totalPages() {
        int totalCount = boardMapper.countAll();
        return (int) Math.ceil((double) totalCount / PAGE_SIZE);
    }

    // 해당 id 페이지
    public List<Board> listPagedById(String id, int page) {
        int offset = page * PAGE_SIZE;
        return boardMapper.boardListPagedById(id, offset, PAGE_SIZE);
    }

    // 해당 id 총 페이지
    public int totalPagesById(String id) {
        int totalCount = boardMapper.countById(id);
        return (int) Math.ceil((double) totalCount / PAGE_SIZE);
    }
}