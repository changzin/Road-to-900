package com.backend.roadto900.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NoteWordDto {
    private int noteId;
    private String noteName;

    List<WordDto> wordDtoList;
}
