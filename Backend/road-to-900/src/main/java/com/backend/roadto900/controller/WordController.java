package com.backend.roadto900.controller;

import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.req.WordAskReq;
import com.backend.roadto900.req.WordDeleteReq;
import com.backend.roadto900.req.WordInsertReq;
import com.backend.roadto900.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WordController {
    private final WordService wordService;



    @GetMapping("/word/list")
    public ResponseEntity findAll(){
        List<WordDto> wordDto = wordService.findAll();
        System.out.println("wordDto = " + wordDto);
        return ResponseEntity.status(201).body(wordDto);
    }

    @PostMapping ("/word/create")
    public ResponseEntity insertWord(@RequestBody WordInsertReq wordInsertReq){
        WordDto wordDto = wordService.insertWord(wordInsertReq);
        System.out.println(wordDto.getSpell());
        return ResponseEntity.status(HttpStatus.CREATED).body(wordDto);
    }

    @PostMapping("/word/delete")
    public ResponseEntity deleteWord(@RequestBody WordDeleteReq deleteWordReq){
        List<WordDto> wordDto = wordService.deleteWord(deleteWordReq);
        return ResponseEntity.status(201).body(wordDto);
    }

    @GetMapping ("/word")
    public ResponseEntity searchWord(@RequestParam String spell){
        WordDto wordDto = wordService.searchWord(spell);
        return ResponseEntity.status(201).body(wordDto);
    }

    @PostMapping("/wordAdd")
    public ResponseEntity askWord(@RequestBody WordAskReq wordAskReq){
        WordDto wordDto = wordService.askWord(wordAskReq);
        return ResponseEntity.status(201).body(wordDto);
    }

}
