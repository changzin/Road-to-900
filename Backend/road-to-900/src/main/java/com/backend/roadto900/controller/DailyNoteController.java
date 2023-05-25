package com.backend.roadto900.controller;

import com.backend.roadto900.config.DailyNote;
import com.backend.roadto900.dto.QuestionDto;
import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.service.DailyNoteService;
import com.backend.roadto900.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DailyNoteController {
    private final DailyNoteService dailyNoteService;
    private final TestService testService;
    
    @GetMapping("/dailyNote")
    public ResponseEntity getDailyNote(){
        List<WordDto> wordDtoList = dailyNoteService.getDailyNote();
        return ResponseEntity.ok().body(wordDtoList);
    }

    @GetMapping("/dailyNote/test")
    public ResponseEntity getDailyNoteTest(){
        List<QuestionDto> questionDtoList = testService.dailyNoteTest();
        return ResponseEntity.ok().body(questionDtoList);
    }
}
