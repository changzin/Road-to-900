package com.backend.roadto900.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WordDto {

    private int wordId;
    private String spell;
    private String mean;

    public String toString() {
        return "WordDto [getSpell=" + getSpell() + ", mean=" + getMean() + "]";
    }

}
