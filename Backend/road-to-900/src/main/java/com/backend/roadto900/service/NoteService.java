package com.backend.roadto900.service;

import com.backend.roadto900.dto.NoteDto;
import com.backend.roadto900.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    public List<NoteDto> findAll(){
        return noteRepository.findAll();
    }
    public List<NoteDto> join(String noteName){return noteRepository.save(noteName);}
    public List<NoteDto> delete(int noteId){return noteRepository.delete(noteId);}

}
