package com.encore.board.author.repository;

import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.QAuthor;
import com.encore.board.post.domain.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryCustom {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QAuthor author = QAuthor.author;
    QPost post = QPost.post;

    Optional<Author> findByEmailOrderByCreated(String email) {
        return Optional.ofNullable(queryFactory.select(author)
                .from(author)
                .join(post)
                .where(author.email.eq(email)).orderBy(post.createdTime.desc()).fetchOne());
    }
}
