package com.backend.roadto900.service;

import com.backend.roadto900.config.DailyNote;
import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.exception.GeneralException;
import com.backend.roadto900.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyNoteService {
    private final WordRepository wordRepository;
    private final NowUser nowUser;
    private final DailyNote dailyNote;

    public List<WordDto> getDailyNote(){
        if (nowUser.getRole() != 0){
            throw new GeneralException("오늘의 단어장 사용 권한이 없습니다", 403);
        }

        dailyNote.updateDailyNote(wordRepository.findAll());
        List<WordDto> DailyNote = dailyNote.getDailyNote(nowUser.getDailyNoteNum());
        if (DailyNote == null || DailyNote.size() != nowUser.getDailyNoteNum()){
            throw new GeneralException("오늘의 단어장을 불러올 수 없습니다", 404);
        }
        return DailyNote;
    }

    public List<WordDto> getFirstTestNote(){
        if (nowUser.getRole() != 0){
            throw new GeneralException("평가 테스트 권한이 없습니다.", 403);
        }
        dailyNote.updateDailyNote(wordRepository.findAll());
        List<WordDto> FirstTestNote = dailyNote.getFirstTestNote();
        if (FirstTestNote == null || FirstTestNote.isEmpty()){
            throw new GeneralException("오늘의 단어장을 불러올 수 없습니다", 404);
        }
        return FirstTestNote;
    }
}
