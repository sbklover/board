package com.board.board.mapper;

import com.board.board.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    int insertMember(Member member);
    Member findById(String id);
}
