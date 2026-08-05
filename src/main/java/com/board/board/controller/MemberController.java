package com.board.board.controller;

import com.board.board.dto.Member;
import com.board.board.service.BoardService;
import com.board.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member")
    public String findById(@RequestParam String id, @RequestParam String password, Model model) {
        Member member = memberService.login(id, password);

        if (member == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 틀립니다.");
            return "login";
        }

        return "redirect:/board"; // BoardController의 페이징 목록 화면으로 이동
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String insertRegister(Member member, Model model) {
        // 아이디 중복 확인
        Member existing = memberService.findById(member.getId());

        if (existing != null) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            model.addAttribute("member", member); // 입력값 유지용
            return "register";
        }

        memberService.insertMember(member);
        return "redirect:/";
    }
}