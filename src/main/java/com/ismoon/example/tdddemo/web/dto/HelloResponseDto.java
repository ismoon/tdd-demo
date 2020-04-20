package com.ismoon.example.tdddemo.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // final로 선언된 모든 필드가 포함된 생성자를 생성한다. 단 final이 없는 필드는 포함되지 않는다.
public class HelloResponseDto {

    private final String name;

    private final int amount;
}
