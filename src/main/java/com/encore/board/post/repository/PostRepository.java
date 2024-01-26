package com.encore.board.post.repository;

import com.encore.board.author.domain.Author;
import com.encore.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //Pageable 객체 pageNumber(page=1), page마다 갯수(size=10), 정렬(sort=createdTime,desc)
    //Page : List<Post> + 해당 Page의 각종 정보
    Page<Post> findAll(Pageable pageAble);
    Page<Post> findByAppointment(String appointment, Pageable pageAble);
    List<Post> findAllByOrderByCreatedTimeDesc();
    //where 로 걸러야 할 때
    //아래 jpql의 join문은 author 객체를 통해 post를 스크리닝하고 싶은 상황에 적합
    //select p.* form post p left join author a on p.author_id = a.id;
    @Query("select p from Post p left join p.author") // jpql 문
    List<Post> findAllJoin();

    //author 객체가 들어있다.
    @Query("select p from Post p left join fetch p.author")
    List<Post> findAllFetchJoin();


}
