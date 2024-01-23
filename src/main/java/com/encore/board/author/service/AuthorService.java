package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import com.encore.board.author.dto.AuthorRespDTO;
import com.encore.board.author.dto.AuthorSaveReqDTO;
import com.encore.board.author.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public void save(AuthorSaveReqDTO authorSaveReqDTO) {

        Role role = null;
        if(authorSaveReqDTO.getRole() == null || authorSaveReqDTO.getRole().equals("user")) {
            role =Role.USER;
        } else {
            role = Role.ADMIN;
        }

        Author author = Author.builder()
                .role(role)
                .name(authorSaveReqDTO.getName())
                .password(authorSaveReqDTO.getPassword())
                .email(authorSaveReqDTO.getEmail())
                .build();
        repository.save(author);

    }

    public List<AuthorRespDTO> findAll() {

        List<Author> authors = repository.findAll();


        return authors.stream().map(AuthorRespDTO::of).collect(Collectors.toList());
    }

    public AuthorRespDTO findById(Long id) {
        Author author = repository.findById(id).orElseThrow(()-> new NoSuchElementException("찾는 회원이 없습니다."));
        return AuthorRespDTO.of(author);
    }

    public AuthorRespDTO update(Long id, AuthorSaveReqDTO authorSaveReqDTO) {
        Author author = repository.findById(id).orElseThrow(()-> new NoSuchElementException("수정할 회원이 없습니다."));
        author.update(author,authorSaveReqDTO);

        repository.save(author);

        return AuthorRespDTO.of(author);
    }

    public void delete(Long id) {
        Author author = repository.findById(id).orElseThrow(()-> new NoSuchElementException("삭제할 회원이 없습니다."));
        repository.delete(author);

    }

}
