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

        if (member == null) {
            model.addAttribute("error", "존재하지 않는 아이디입니다.");
            return "login";  // 로그인 화면으로 다시
        }

        if (!member.getPassword().equals(password)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
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
