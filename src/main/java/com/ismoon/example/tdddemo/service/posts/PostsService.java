package com.ismoon.example.tdddemo.service.posts;

import com.ismoon.example.tdddemo.domain.posts.PostsRepository;
import com.ismoon.example.tdddemo.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    // 생성자를 통해서 Bean이 주입될 수 있도록 하기 위해서 @Autowired를 사용하지 않음
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
