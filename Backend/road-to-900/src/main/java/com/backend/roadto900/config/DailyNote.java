package com.backend.roadto900.config;

import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.repository.WordRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class DailyNote {

    private List<WordDto> dailyNote;
    private LocalDate updateDate;

    // 비어있거나, 날짜가 다르다면 업데이트하는 시나리오
    public void updateDailyNote(List<WordDto> wordDtoList){
        if (updateDate == null || !updateDate.equals(LocalDate.now())){
            dailyNote.clear();
            Collections.shuffle(wordDtoList);
            for(int i = 0; i < 30; i++){
                dailyNote.add(wordDtoList.get(i));
            }
        }
    }

    public List<WordDto> getDailyNote(int dailyNoteNum){
        if (dailyNoteNum == 10 || dailyNoteNum == 20 || dailyNoteNum == 30)
            return dailyNote.subList(0, dailyNoteNum);
        return null;
    }
}
