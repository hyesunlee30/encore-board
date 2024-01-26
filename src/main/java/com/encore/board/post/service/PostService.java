package com.encore.board.post.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.AuthorRespDTO;
import com.encore.board.author.dto.AuthorSaveReqDTO;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.author.service.AuthorService;
import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.PostSaveReqDTO;
import com.encore.board.post.dto.PostListResDTO;
import com.encore.board.post.dto.PostUpdateReqDTO;
import com.encore.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository repository;
    private final AuthorRepository authorRepository;

    @Autowired
    public PostService(PostRepository repository,AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }



    public void save(PostSaveReqDTO postSaveReqDTO) {
        Author author = authorRepository.findByEmail(postSaveReqDTO.getAuthor_email()).orElse(null);
        LocalDateTime localDateTime = null;
        String appointment = null;
        if(postSaveReqDTO.getAppointment().equals("Y")
                && !postSaveReqDTO.getAppointmentTime().isEmpty()) {
            appointment = "Y";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            localDateTime = LocalDateTime.parse(postSaveReqDTO.getAppointmentTime(),dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(localDateTime.isBefore(now)) {
                throw new IllegalArgumentException("현재보다 전 시간은 선택할 수 없습니다.");
            }
        }


        Post post = Post.builder()
                .title(postSaveReqDTO.getTitle())
                .contents(postSaveReqDTO.getContents())
                .author(author)
                .appointment(appointment)
                .appointmentTime(localDateTime)
                .build();

        //더티체킹 테스트
        //post save 할때 author르 업데이트 하면 author를 save 안 해도 알아서 해줄까
        //author.updateAuthor("dirtyCheck","1234");

        repository.save(post);

    }

    public Page<PostListResDTO> findAll(Pageable pageable) {
        Page<Post> posts = repository.findAll(pageable);
        return posts.map(PostListResDTO::of);
    }


    public Page<PostListResDTO> findByAppointment(Pageable pageable) {
        Page<Post> posts = repository.findByAppointment(null,pageable);
        return posts.map(PostListResDTO::of);
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
