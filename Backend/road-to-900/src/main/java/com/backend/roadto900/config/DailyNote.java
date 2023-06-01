package com.backend.roadto900.config;

import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.repository.WordRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DailyNote {

    private List<WordDto> dailyNote = new ArrayList<>();
    private List<WordDto> firstTestNote = new ArrayList<>();
    private LocalDate updateDate;

    // 비어있거나, 날짜가 다르다면 업데이트하는 시나리오
    public void updateDailyNote(List<WordDto> wordDtoList){
        int cnt = 0;
        if (updateDate == null || !updateDate.equals(LocalDate.now())){
            updateDate = LocalDate.now();
            dailyNote.clear();
            firstTestNote.clear();
            Collections.shuffle(wordDtoList);
            for (WordDto wordDto : wordDtoList) {
                dailyNote.add(wordDto);
                cnt++;
                if (cnt == 30) {
                    break;
                }
            }
            cnt = 0;
            Collections.shuffle(wordDtoList);
            for (WordDto wordDto : wordDtoList) {
                firstTestNote.add(wordDto);
                cnt++;
                if (cnt == 10) {
                    break;
                }
            }
        }
    }

    public List<WordDto> getDailyNote(int dailyNoteNum) {
        if (dailyNote.isEmpty() || dailyNote.size() < dailyNoteNum) {
            return null;
        }
        if (dailyNoteNum == 10 || dailyNoteNum == 20 || dailyNoteNum == 30 || dailyNoteNum <= 5)
            return dailyNote.subList(0, dailyNoteNum);
        return null;
    }

    public List<WordDto> getFirstTestNote(){
        return this.firstTestNote;
    }
}
