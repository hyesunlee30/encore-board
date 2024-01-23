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

    @GetMapping("/author/create")
    public String create() {
        return "author/author-create";
    }


    @PostMapping("/author/create")
    public String authorSave(AuthorSaveReqDTO authorSaveReqDTO, Model model) {
        service.save(authorSaveReqDTO);
        model.addAttribute("authorList",service.findAll());
        return "author/author-list";
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

    @PostMapping("/author/update/{id}")
    public String authorUpdate(@PathVariable(value = "id")Long id, AuthorSaveReqDTO authorSaveReqDTO){
        return "redirect:/author/detail/"+id;
    }

    @GetMapping("/author/delete/{id}")
    public String authorDelete(@PathVariable(value="id")Long id, Model model){
        service.delete(id);
        return "redirect:/author/list";
    }

}
