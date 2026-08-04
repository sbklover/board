package com.board.board.mapper;

import com.board.board.dto.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    // 글쓰기
    void boardWrite(Board board);

    // 글 수정
    void boardModify(Board board);

    // 글 삭제
    void boardDelete(Long no);

    // 전체 리스트 (나중에 수정 해야 함)
    List<Board> listAll();
}
