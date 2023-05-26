package com.backend.roadto900.req;

import com.backend.roadto900.dto.WordAddDto;
import lombok.Getter;

import java.util.List;

@Getter
public class WordAddDeleteReq {
    private int wordAddId;
    private String wordAddSpell;
    private List<Integer> wordList;

}
