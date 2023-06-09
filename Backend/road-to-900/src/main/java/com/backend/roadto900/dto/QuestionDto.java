package com.backend.roadto900.dto;

import com.backend.roadto900.dto.WordDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Getter
@AllArgsConstructor
public class QuestionDto {
    private WordDto wordDto;
    private int answer;
    private String[] choice = new String[4];

    public QuestionDto(WordDto wordDto, List<WordDto> wordDtoList){
        this.wordDto = new WordDto(wordDto.getWordId(), wordDto.getSpell(), wordDto.getMean());
        makeAnswer();
        makeChoice(wordDtoList);
    }
    private void makeAnswer(){
        Random rand = new Random();
        this.answer = rand.nextInt(4);
        choice[answer] = wordDto.getMean();
    }

    private void makeChoice(List<WordDto> wordDtoList){
        Collections.shuffle(wordDtoList);
        int cnt = 0;
        for (int i = 0; i < wordDtoList.size(); i++){
            if (cnt == answer){
                cnt++;
            }
            else if (!wordDto.getMean().equals(wordDtoList.get(i).getMean())){
                choice[cnt] = wordDtoList.get(i).getMean();
                cnt++;
            }
            if (cnt == 4){
                break;
            }
        }
    }
}
