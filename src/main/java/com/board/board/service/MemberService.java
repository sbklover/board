package com.board.board.service;

import com.board.board.dto.Member;
import com.board.board.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;

    public Member findById(String id) {
        return memberMapper.findById(id);
    }
}
