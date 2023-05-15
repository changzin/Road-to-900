package com.backend.roadto900.service;

import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.repository.WordRepository;
import com.backend.roadto900.req.WordDeleteReq;
import com.backend.roadto900.req.WordInsertReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;

    public List<WordDto> findAll() {
        return wordRepository.findAll();

    }

    public WordDto insertWord(WordInsertReq wordInsertReq) {

        return wordRepository.insertWord(wordInsertReq);
    }

    public List<WordDto> deleteWord(WordDeleteReq deleteWordReq){

        return wordRepository.deleteWord(deleteWordReq);
    }

    public WordDto searchWord(String spell) {
        return wordRepository.searchWord(spell);
    }
}
