package com.board.board.controller;

import com.board.board.dto.Board;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    // 게시글 목록 화면 (7개씩 페이징, page는 0부터 시작)
    @GetMapping("/board")
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        int totalPages = boardService.totalPages();
        if (page < 0) {
            page = 0;
        } else if (totalPages > 0 && page > totalPages - 1) {
            page = totalPages - 1;
        }

        model.addAttribute("boardList", boardService.listPaged(page));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "list"; // templates/list.html
    }

    // 새 글쓰기 폼 화면
    @GetMapping("/board/new")
    public String newForm() {
        return "new"; // templates/new.html
    }

    // 새 글쓰기 폼 제출 처리
    @PostMapping("/board/new")
    public String write(Board board) {
        boardService.InsertBoard(board);
        return "redirect:/board";
    }

    // 글 수정 폼 화면 (기존 데이터 채워서 보여줌)
    @GetMapping("/board/modify/{no}")
    public String modifyForm(@PathVariable("no") Long no, Model model) {
        Board board = boardService.boardView(no);
        model.addAttribute("board", board);
        return "modify"; // templates/modify.html
    }

    // 글 수정 폼 제출 처리
    @PostMapping("/board/modify")
    public String modify(Board board) {
        boardService.boardModify(board);
        return "redirect:/board"; // 저장 후 목록으로 이동하며 최신 데이터 재조회
    }

    // 글 삭제 처리
    @GetMapping("/board/delete/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.boardDelete(no);
        return "redirect:/board";
    }
}