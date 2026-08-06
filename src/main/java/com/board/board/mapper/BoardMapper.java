package com.board.board.mapper;

import com.board.board.dto.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    // 글쓰기
    void InsertBoard(Board board);

    // 글 수정
    void boardModify(Board board);

    // 글 삭제
    void boardDelete(@Param("no") Long no);

    // 전체 리스트 (나중에 수정 해야 함)
    List<Board> listAll();

    // 페이징 목록 조회
    List<Board> boardListPaged(@Param("offset") int offset, @Param("size") int size);

    // 전체 게시글 개수
    int countAll();

    // 단건 조회 (수정 폼에 기존 데이터 채울 때 사용)
    Board boardView(@Param("no") Long no);

    // 해당 ID 페이지 조회
    List<Board> boardListPagedById(@Param("id") String id, @Param("offset") int offset, @Param("size") int size);

    // 해당 ID 건수
    int countById(@Param("id") String id);
}