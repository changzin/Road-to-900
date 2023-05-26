package com.backend.roadto900.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WordAddDto {
    // 유저의 단어 요청시 word_add 테이블 확인 위해 생성
    private int wordId;
    private String spell;


}
