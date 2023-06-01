package com.backend.roadto900.controller;

import com.backend.roadto900.dto.WordAddDto;
import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.req.WordAddDeleteReq;
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
        List<WordDto> wordDtoList = wordService.findAll();
        return ResponseEntity.ok().body(wordDtoList);
    }

    @PostMapping ("/word/create")
    public ResponseEntity insertWord(@RequestBody WordInsertReq wordInsertReq){
        String result = wordService.insertWord(wordInsertReq);
        return ResponseEntity.status(201).body(result);
    }

    @PostMapping("/word/delete")
    public ResponseEntity deleteWord(@RequestBody List<Integer> deleteWordIdList){
        String result = wordService.deleteWord(deleteWordIdList);
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping ("/word")
    public ResponseEntity searchWord(@RequestParam String spell){
        List<WordDto> wordDto = wordService.searchWord(spell);
        return ResponseEntity.status(201).body(wordDto.get(0));
    }

    @PostMapping("/wordAdd")
    public ResponseEntity askWord(@RequestBody WordAskReq wordAskReq){
        String result = wordService.askWord(wordAskReq);
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/wordAdd/list")
    public ResponseEntity findAskWord() {
        List<WordAddDto> wordAddDtoList = wordService.findAskWord();
        return ResponseEntity.status(201).body(wordAddDtoList);
    }

    @PostMapping("/wordAdd/delete")
    public ResponseEntity deleteAskWord(@RequestBody List<Integer> deleteAskWordIdList) {
        String result = wordService.deleteAskWord(deleteAskWordIdList);
        return ResponseEntity.status(201).body(result);
    }

}
