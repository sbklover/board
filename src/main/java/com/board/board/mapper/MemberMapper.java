package com.board.board.mapper;

import com.board.board.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    Member findById(String id);
}
