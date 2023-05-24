package com.backend.roadto900.service;

import com.backend.roadto900.dto.NoteDto;
import com.backend.roadto900.dto.NoteWordDto;
import com.backend.roadto900.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepositoryImpl;
    public List<NoteDto> findAll(){
        return noteRepositoryImpl.findAll();
    }
    public List<NoteDto> join(String noteName){return noteRepositoryImpl.save(noteName);}
    public List<NoteDto> delete(int noteId){return noteRepositoryImpl.delete(noteId);}
    public NoteWordDto findNoteWord(int noteId){return noteRepositoryImpl.findNoteWord(noteId);}
}
