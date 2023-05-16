package com.backend.roadto900.service;

import com.backend.roadto900.config.DailyNote;
import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.WordDto;
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

    private List<WordDto> getDailyNote(){
        dailyNote.updateDailyNote(wordRepository.findAll());
        return dailyNote.getDailyNote(nowUser.getDailyNoteNum());
    }
}
