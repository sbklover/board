package com.board.board.service;

import com.board.board.dto.Member;
import com.board.board.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    // 유저 등록
    public String insertMember(Member member) {
        // 비밀번호 암호화 후 저장
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        memberMapper.insertMember(member);

        return "redirect:/";
    }

    // 유저 찾기
    public Member findById(String id) {
        return memberMapper.findById(id);
    }

    // 로그인 검증: 아이디 존재 여부 + 암호화된 비밀번호 일치 여부를 한 번에 처리
    public Member login(String id, String rawPassword) {
        Member member = memberMapper.findById(id);

        if (member == null) {
            return null; // 아이디 없음
        }

        boolean isMatch = passwordEncoder.matches(rawPassword, member.getPassword());
        if (!isMatch) {
            return null; // 비밀번호 불일치
        }

        return member; // 로그인 성공
    }
}