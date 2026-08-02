package com.board.board.controller;

import com.board.board.dto.Member;
import com.board.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final MemberService memberService;

    @GetMapping("/member")
    public String findById(@RequestParam String id, Model model) {
        Member member = memberService.findById(id);

        System.out.println(member);

        model.addAttribute("member", member);

        return "member";
    }
}
