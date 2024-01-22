package com.encore.board.author.controller;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.AuthorRespDTO;
import com.encore.board.author.dto.AuthorSaveReqDTO;
import com.encore.board.author.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping("/author/save")
    @ResponseBody
    public String authorSave(AuthorSaveReqDTO authorSaveReqDTO) {
        service.save(authorSaveReqDTO);
        return "OK";
    }

    @GetMapping("/author/list")
    public String authorList(Model model) {
         model.addAttribute("authorList",service.findAll());
        return "author/author-list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable(value = "id")Long id,Model model) {
        model.addAttribute("author",service.findById(id));
        return "author/author-detail";
    }
}
