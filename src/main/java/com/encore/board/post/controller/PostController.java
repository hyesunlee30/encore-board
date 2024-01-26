package com.encore.board.post.controller;

import com.encore.board.post.dto.PostListResDTO;
import com.encore.board.post.dto.PostSaveReqDTO;
import com.encore.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PostController {

    private final PostService service;

    @Autowired
    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping("/post/create")
    public String create() {
        return "post/post-create";
    }


    @PostMapping("/post/create")
    public String postSave(PostSaveReqDTO postSaveReqDTO, Model model, Pageable pageable) {
        try {
            service.save(postSaveReqDTO);
            return "redirect:/post/list";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "post/post-create";
        }

    }

    @GetMapping("/post/list")
    public String postList(@PageableDefault(size=5, sort = "createdTime",direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("postList",service.findByAppointment(pageable));
        return "post/post-list";
    }


    @GetMapping("/post/detail/{id}")
    public String postDetail(@PathVariable(value = "id")Long id, Model model) {
        model.addAttribute("post",service.findById(id));
        return "post/post-detail";
    }

    @PostMapping("/post/update/{id}")
    public String postUpdate(@PathVariable(value = "id")Long id, PostSaveReqDTO postSaveReqDTO){
        return "redirect:/post/detail/"+id;
    }

    @GetMapping("/post/delete/{id}")
    public String postDelete(@PathVariable(value="id")Long id, Model model){
        service.delete(id);
        return "redirect:/post/list";
    }

}
