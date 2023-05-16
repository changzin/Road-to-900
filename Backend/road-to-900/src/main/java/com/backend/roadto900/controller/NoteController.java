package com.backend.roadto900.controller;

import com.backend.roadto900.dto.NoteDto;
import com.backend.roadto900.dto.NoteWordDto;
import com.backend.roadto900.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("note/list")
    public ResponseEntity noteList(){
        List<NoteDto> noteDtoList = noteService.findAll();
        return ResponseEntity.status(200).body(noteDtoList);
    }

    @PostMapping("note/delete")
    public ResponseEntity delete(@RequestParam int noteId){
        List<NoteDto> noteDtoList = noteService.delete(noteId);
        return ResponseEntity.status(200).body(noteDtoList);
    }

    @PostMapping("note/create")
    public ResponseEntity create(@RequestParam String noteName){
        List<NoteDto> noteDtoList = noteService.join(noteName);;
        return ResponseEntity.status(200).body(noteDtoList);
    }

    @GetMapping("note/{noteId}")
    public ResponseEntity noteWordList(@PathVariable int noteId){
        NoteWordDto noteWordDto = noteService.findNoteWord(noteId);
        return ResponseEntity.status(200).body(noteWordDto);
    }

    @GetMapping("note/{noteId}/test")
    public ResponseEntity noteTest(){
        return null;
    }

    @GetMapping("note/{noteId}/create")
    public ResponseEntity noteWordCreate(){
        return null;
    }

    @GetMapping("note/{noteId}/delete")
    public ResponseEntity noteWordDelete(){
        return null;
    }
}
