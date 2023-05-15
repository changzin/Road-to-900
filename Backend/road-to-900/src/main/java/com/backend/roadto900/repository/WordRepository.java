package com.backend.roadto900.repository;

import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.req.WordDeleteReq;
import com.backend.roadto900.req.WordInsertReq;

import java.util.List;

public interface WordRepository {
//    WordDto insertWord (WordDto wordDto);  // 단어 추가
    WordDto insertWord(WordInsertReq wordInsertReq);
    List<WordDto> findAll();    // 모든 단어 불러오기
    List<WordDto> deleteWord(WordDeleteReq deleteWordReq);


}


