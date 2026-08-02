package com.board.board.controller;

import com.board.board.dto.Member;
import com.board.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final MemberService memberService;

    @PostMapping("/member")
    public String findById(@RequestParam String id, @RequestParam String password, Model model) {
        Member member = memberService.findById(id);

        if (member == null || !member.getPassword().equals(password)) {
            model.addAttribute("error", "아이디 또는 비밀번호가 틀립니다.");
            return "login";
        }

        model.addAttribute("member", memberService.findById(id));
        return "member";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }
}
