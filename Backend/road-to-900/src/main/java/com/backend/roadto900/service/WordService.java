package com.backend.roadto900.service;

import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.WordAddDto;
import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.exception.GeneralException;
import com.backend.roadto900.exhandler.GlobalExceptionHandler;
import com.backend.roadto900.repository.WordRepository;
import com.backend.roadto900.req.WordAddDeleteReq;
import com.backend.roadto900.req.WordAskReq;
import com.backend.roadto900.req.WordDeleteReq;
import com.backend.roadto900.req.WordInsertReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;
    private final NowUser nowUser;
    private int num=0;

    public List<WordDto> findAll() {
        num = (wordRepository.findAll()).size();
        if ( num == 0 ){
            throw new GeneralException("전체 단어장에 단어가 없습니다.", 404);
        }
        return wordRepository.findAll();
    }

    public String insertWord(WordInsertReq wordInsertReq) {
        List<WordDto> searchResults = wordRepository.searchWord(wordInsertReq.getSpell());
        if (!searchResults.isEmpty()){
            throw new GeneralException("잘못된 입력 형식, 단어 중복", 400);
        } else if (wordInsertReq.getSpell().length()>20) {
            throw new GeneralException("단어 글자 수 초과", 400);
        } else if (wordInsertReq.getSpell().length()==0) {
            throw new GeneralException("잘못된 입력 형식, 단어 중복", 400);
        }
        if (nowUser.getRole() != 1){
            throw new GeneralException("단어 추가 권한이 없습니다.", 403);
        }
        wordRepository.insertWord(wordInsertReq);
        return "단어 추가 완료 되었습니다.";
    }

    public String deleteWord(List<Integer> deleteWordIdList){
        if ( nowUser.getRole() != 1) {
            throw new GeneralException("단어 삭제 권한이 없음.", 403);
        }
        if (deleteWordIdList == null || deleteWordIdList.isEmpty()){
            throw new GeneralException("삭제할 단어를 선택하지 않음", 404);
        }
        for(int deleteWordId : deleteWordIdList){
            wordRepository.deleteWord(deleteWordId);
        }
        return "단어 삭제 완료";
    }

    public List<WordDto> searchWord(String spell) {
        List<WordDto> wordList = wordRepository.searchWord(spell);

        if (wordList == null || wordList.isEmpty()) {
            throw new GeneralException("스펠링과 일치하는 단어를 찾을 수 없음.", 404);
        }
        if (wordList.size() != 1){
            throw new GeneralException("단어를 찾을 수 없습니다.", 404);
        }
        return wordList;
    }

    public String askWord(WordAskReq wordAskReq) {
        if ( wordAskReq.getSpell().isBlank() ) {
            throw new GeneralException("잘못된 형식의 단어 요청 입력.", 400);
        } else if (wordAskReq.getSpell().length()>20) {
            throw new GeneralException("잘못된 입력 형식, 단어 중복", 404);
        } else if (wordAskReq.getSpell().length()==0) {
            throw new GeneralException("잘못된 입력 형식, 단어 중복", 404);
        }
        wordRepository.askWord(wordAskReq);
        return "단어 요청 추가 완료";
    }

    public List<WordAddDto> findAskWord() {
        num = (wordRepository.findAskWord()).size();
        if(nowUser.getRole() != 1){
            throw new GeneralException("단어 추가 요청 조회 권한이 없음", 403);
        }else if ( num == 0 ){
            throw new GeneralException("단어 추가 요청을 조회할 수 없음.", 404);
        }
        return wordRepository.findAskWord();
    }

    public String deleteAskWord(List<Integer> deleteAskWordIdList) {
        if (nowUser.getRole() != 1){    // 관리자가 아니면
            throw new GeneralException("단어 삭제 권한이 없음", 403);
        }
        if (deleteAskWordIdList == null || deleteAskWordIdList.isEmpty()){
            throw new GeneralException("삭제할 단어 요청을 선택하지 않음", 404);
        }
        for (int deleteAskWordId : deleteAskWordIdList){
            wordRepository.deleteAskWord(deleteAskWordId);
        }
        return "단여 요청 삭제 완료";

    }

}
