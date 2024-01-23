package com.encore.board.post.service;

import com.encore.board.author.dto.AuthorRespDTO;
import com.encore.board.author.dto.AuthorSaveReqDTO;
import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.PostSaveReqDTO;
import com.encore.board.post.dto.PostListResDTO;
import com.encore.board.post.dto.PostUpdateReqDTO;
import com.encore.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository repository;

    @Autowired
    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public void save(PostSaveReqDTO postSaveReqDTO) {
        Post post = Post.builder()
                .title(postSaveReqDTO.getTitle())
                .contents(postSaveReqDTO.getContents())
                .build();
        repository.save(post);

    }

    public List<PostListResDTO> findAll() {
        List<Post> posts = new ArrayList<>();
        posts = repository.findAll();

        return posts.stream().map(PostListResDTO::of).collect(Collectors.toList());
    }

    public PostListResDTO findById(Long id) {
        Post post = repository.findById(id).orElseThrow(()-> new NoSuchElementException("찾는 글이 없습니다."));
        return PostListResDTO.of(post);
    }

    public void update(Long id, PostUpdateReqDTO postUpdteReqDTO) {

        Post post = repository.findById(id).orElseThrow(()-> new NoSuchElementException("찾는 글이 없습니다."));

        post = Post.updatePost(post, postUpdteReqDTO);
        repository.save(post);

    }

    public void delete(Long id) {

        Post post = repository.findById(id).orElseThrow(()-> new NoSuchElementException("삭제할 글이 없습니다."));

        repository.delete(post);

    }
}
