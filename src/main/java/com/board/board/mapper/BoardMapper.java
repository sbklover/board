package com.board.board.mapper;

import com.board.board.dto.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> listAll();
}
