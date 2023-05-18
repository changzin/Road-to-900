package com.backend.roadto900.service;

import com.backend.roadto900.config.DailyNote;
import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.NoteDto;
import com.backend.roadto900.dto.NoteWordDto;
import com.backend.roadto900.dto.QuestionDto;
import com.backend.roadto900.dto.WordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final NoteService noteService;
    private final WordService wordService;
    private final NowUser nowUser;
    private final DailyNote dailyNote;

    public List<QuestionDto> makeNoteTest(int noteId){
        // 시험 볼 노트의 단어들 가져옴
        NoteWordDto noteWordDto = noteService.findNoteWord(noteId);

        return makeTest(noteWordDto.getWordDtoList());
    }

    public List<QuestionDto> dailyNoteTest(){
        dailyNote.updateDailyNote(wordService.findAll());
        List<WordDto> wordDtoList = dailyNote.getDailyNote(nowUser.getDailyNoteNum());
        return makeTest(wordDtoList);
    }


    private List<QuestionDto> makeTest(List<WordDto> wordDtoList){
        // 보기를 만들기 위해 전체 단어 가져옴
        List<WordDto> wholeWordList = wordService.findAll();

        List <QuestionDto> questionDtoList = new ArrayList<>();
        // 문제 만들어서 넣기
        for(WordDto wordDto : wordDtoList){
            QuestionDto questionDto = new QuestionDto(wordDto, wholeWordList);
            questionDtoList.add(questionDto);
        }
        // 문제 순서 섞기
        Collections.shuffle(questionDtoList);
        return questionDtoList;
    }
}
