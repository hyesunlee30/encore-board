package com.encore.board.post.service;

import com.encore.board.post.domain.Post;
import com.encore.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostScheduler {
    private final PostRepository postRepository;

    @Autowired
    public PostScheduler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // * : 매 초(분/시 등을) 의미
    // 특정 숫자: 특정 숫자의 분시를 의미
    // 초 분 시 일 월 요일 형태로 스케쥴링 설정
    // 0/특정숫자 : 특정 숫자 마다
    //ex) 0 0 * * * * : 매일 0분 0초에 스케쥴링 시작
    //ex) 0/1 * * * * : 매일 1분마다 0초에 스케쥴링 시작
    //ex) 0 0/10 * * * * : 매일 10분마다 0초에 스케쥴링 시작
    //ex) 0/1 * * * * * : 매초마다
    //ex) 0 0 11 * * *  : 매일 11시에 스케쥴링
    @Scheduled(cron = "0 0/1 * * * *")
    @Transactional
    public void postScheduler() {
        System.out.println("====스케쥴러 시작====");

        // 페이징을 안 하겠다는 뜻 Pageable.unpaged()
        Page<Post> posts = postRepository.findByAppointment("Y", Pageable.unpaged());
        for (Post post : posts) {
            LocalDateTime now = LocalDateTime.now();
            if(now.isBefore(post.getAppointmentTime())) {
                post.updateAppointment(null);
                postRepository.save(post);
            }
        }

        System.out.println(posts.getSize());

        System.out.println("====스케쥴러 끝====");
    }
}
