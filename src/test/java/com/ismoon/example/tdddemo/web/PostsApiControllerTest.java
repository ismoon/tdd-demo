package com.ismoon.example.tdddemo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismoon.example.tdddemo.domain.posts.Posts;
import com.ismoon.example.tdddemo.domain.posts.PostsRepository;
import com.ismoon.example.tdddemo.web.dto.PostsSaveRequestDto;
import com.ismoon.example.tdddemo.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postsRegister() throws Exception {
        String title = "테스트 게시글";
        String content = "테스트 본문";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("insu4341@gmail.com")
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        ).andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postsUpdate() throws Exception {
        String title = "테스트 게시글";
        String content = "테스트 본문";
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("insu4341@gmail.com")
                .build()
        );

        Long updateId = savedPosts.getId();
        String exceptedTitle = "title2";
        String exceptedContent = "content2";
        PostsUpdateRequestDto postsUpdateRequestDto = PostsUpdateRequestDto.builder()
                .title(exceptedTitle)
                .content(exceptedContent)
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postsUpdateRequestDto))
        ).andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(exceptedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(exceptedContent);
    }
}