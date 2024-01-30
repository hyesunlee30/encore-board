package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import com.encore.board.author.dto.AuthorRespDTO;
import com.encore.board.author.dto.AuthorSaveReqDTO;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domain.Post;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {
    private final AuthorRepository repository;
    private final PasswordEncoder passwordEncoder;

    AuthorService(AuthorRepository repository,PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;

    }


    public void save(AuthorSaveReqDTO authorSaveReqDTO) throws IllegalArgumentException {

        System.out.println(authorSaveReqDTO.getPw());

        if(repository.findByEmail(authorSaveReqDTO.getEmail()).isPresent()) throw new IllegalArgumentException("중복이메일");

        Role role = null;
        if(authorSaveReqDTO.getRole() == null || authorSaveReqDTO.getRole().equals("user")) {
            role =Role.USER;
        } else {
            role = Role.ADMIN;
        }

        //#####################################################
        //author를 먼저 만드는 이유는 oneTomany
        //author 먼저 만들고
        //post 만들고 -- 만든 author 를 넣고
        //author 에  post 넣고
        //author를 save
        //#####################################################

        //변경된 값 체크하는 것은 cascade랑 상관없는 dirty check
        //oder repo에서 item 테이블을 변경했을경우 jpa에서 체크한다
        //oder 주문 객체에서
        //주문했을때 item 객체를 수량을 변경했다.
        //oder.save 하고 item.save 하지 않아도 order.save 만 해줘도 item.save가 가능하다. -- 더티체킹

        //이건 추가 삭제 같은 경우지 cascade일때이다.
        //컨텍스트(문맥)
        //persist에 객체 미리 캐싱
        //부모 객체만 save 하면 post까지 save -- cascade 옵션 persist이어야 가능한데 All로 해놧기 때문에 가능하다.
        Author author = Author.builder()
                .role(role)
                .name(authorSaveReqDTO.getName())
                .password(passwordEncoder.encode(authorSaveReqDTO.getPw()))
                .email(authorSaveReqDTO.getEmail())
                .build();


        //persist에 컨텍스트(문맥)
        //Author의 객체에 선언되어 있는ㄷ private List<Post> posts;
        List<Post> posts = new ArrayList<>();
        Post post = Post.builder()
                .title(author.getName()+"님이 가입하셨습니다")
                .contents("반갑습니다.")
                .author(author)
                .build();
        posts.add(post);


        author.setPost(posts);
        repository.save(author);

    }

    public List<AuthorRespDTO> findAll() {

        List<Author> authors = repository.findAll();

        return authors.stream().map(AuthorRespDTO::of).collect(Collectors.toList());
    }

    public AuthorRespDTO findById(Long id) throws EntityNotFoundException{
        Author author = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("찾는 회원이 없습니다."));
        return AuthorRespDTO.of(author);
    }

    public Author findById2(Long id) throws EntityNotFoundException {
        System.out.println(id);
        Author author = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("찾는 회원이 없습니다."));
        return author;
    }

    public AuthorRespDTO update(Long id, AuthorSaveReqDTO authorSaveReqDTO) {
        Author author = repository.findById(id).orElseThrow(()-> new NoSuchElementException("수정할 회원이 없습니다."));
        //save를 하지 않아도 변경이 감지되면 자동으로 save 된다.
        author.update(authorSaveReqDTO);
        //author.updateAuthor(authorSaveReqDTO.getName(),authorSaveReqDTO.getPassword());

        repository.save(author);

        return AuthorRespDTO.of(author);
    }


    public void delete(Long id) {
        Author author = repository.findById(id).orElseThrow(()-> new NoSuchElementException("삭제할 회원이 없습니다."));
        repository.delete(author);

    }

}
