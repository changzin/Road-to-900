package com.backend.roadto900.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class NoteDto {
    private int noteId;
    private int userId;
    private String noteName;
}
