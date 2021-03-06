package com.ismoon.example.tdddemo.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository repository;

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }


    @Test
    public void postsSaveAndfindAllTest() {
        String title = "테스트 게시글";
        String content = "테스트 본문";

        repository.save( Posts.builder()
                .title(title)
                .content(content)
                .author("insu4341@gmail.com")
                .build()
        );

        List<Posts> postsList = repository.findAll();
        Posts posts = postsList.get(0);

        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void baseTimeEntity_register() {
        LocalDateTime now = LocalDateTime.of(2020, 4, 21, 0, 0, 0);

        repository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build()
        );

        List<Posts> postsList = repository.findAll();
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>> CreateDate = " + posts.getCreatedDate() + ", ModifiedDate = " + posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}