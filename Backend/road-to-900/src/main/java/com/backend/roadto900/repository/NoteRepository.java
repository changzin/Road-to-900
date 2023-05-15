package com.backend.roadto900.repository;

import com.backend.roadto900.dto.NoteDto;

import java.util.List;

public interface NoteRepository {
    List<NoteDto> findAll();

    List<NoteDto> save(String noteName);

    List<NoteDto> delete(int noteId);

    int countByNoteName(String noteName);
}
