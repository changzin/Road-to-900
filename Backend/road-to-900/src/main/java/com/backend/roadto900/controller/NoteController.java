package com.backend.roadto900.controller;

import com.backend.roadto900.dto.NoteDto;
import com.backend.roadto900.dto.NoteWordDto;
import com.backend.roadto900.dto.QuestionDto;
import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.service.NoteService;
import com.backend.roadto900.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final TestService testService;

    @GetMapping("note/list")
    public ResponseEntity noteList(){
        List<NoteDto> noteDtoList = noteService.findAll();
        return ResponseEntity.ok().body(noteDtoList);
    }

    @PostMapping("note/delete")
    public ResponseEntity delete(@RequestBody List<Integer> noteIdList){
        String result = noteService.delete(noteIdList);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("note/create")
    public ResponseEntity create(@RequestParam String noteName){
        String result = noteService.join(noteName);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("note/{noteId}")
    public ResponseEntity noteWordList(@PathVariable int noteId){
        NoteWordDto noteWordDto = noteService.findNoteWord(noteId);
        return ResponseEntity.ok().body(noteWordDto);
    }

    @GetMapping("note/{noteId}/test")
    public ResponseEntity noteTest(@PathVariable int noteId){
        List<QuestionDto> questionDtoList = testService.makeNoteTest(noteId);
        return ResponseEntity.ok().body(questionDtoList);
    }

    @PostMapping("note/{noteId}/create")
    public ResponseEntity noteWordCreate(@PathVariable int noteId, @RequestBody List<Integer> wordIdList){
        String result = noteService.createNoteWord(noteId, wordIdList);
        return ResponseEntity.status(201).body(result);
    }

    @PostMapping("note/{noteId}/delete")
    public ResponseEntity noteWordDelete(@PathVariable int noteId, @RequestBody List<Integer> wordIdList){
        String result = noteService.deleteNoteWord(noteId, wordIdList);
        return ResponseEntity.ok().body(result);
    }
}
