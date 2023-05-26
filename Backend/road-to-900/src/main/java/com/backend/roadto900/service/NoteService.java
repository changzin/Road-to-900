package com.backend.roadto900.service;

import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.NoteDto;
import com.backend.roadto900.dto.NoteWordDto;
import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.exception.GeneralException;
import com.backend.roadto900.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NowUser nowUser;
    private final NoteRepository noteRepositoryImpl;

    public List<NoteDto> findAll(){
        if (nowUser.getRole() != 0){
            throw new GeneralException("개인 단어장 사용 권한이 없습니다", 403);
        }
        List<NoteDto> noteDtoList = noteRepositoryImpl.findAll();
        if (noteDtoList.isEmpty()){
            throw new GeneralException("사용자의 노트가 없습니다.", 404);
        }
        return noteDtoList;
    }

    public List<NoteDto> join(String noteName){
        int count;

        if (nowUser.getRole() != 0){
            throw new GeneralException("개인 단어장 추가 권한이 없습니다.", 403);
        }
        if (noteName.length() > 20){
            throw new GeneralException("잘못된 단어장 이름 형식 입력입니다.", 400);
        }
        count = noteRepositoryImpl.countByNoteName(noteName);
        if (count != 0){
            throw new GeneralException("중복된 단어장 이름 입력입니다.", 400);
        }

        return noteRepositoryImpl.save(noteName);
    }

    public List<NoteDto> delete(int noteId){
        int count = noteRepositoryImpl.countByNoteId(noteId);
        if (count == 0){
            throw new GeneralException("삭제하려는 개인 단어장을 찾을 수 없습니다.", 404);
        }
        else{
            NoteDto noteDto = noteRepositoryImpl.findByNoteId(noteId);
            if (noteDto.getUserId() != nowUser.getUserId()){
                throw new GeneralException("개인 단어장 삭제 권한이 없습니다.", 403);
            }
        }
        return noteRepositoryImpl.delete(noteId);
    }

    public NoteWordDto findNoteWord(int noteId){
        int count = noteRepositoryImpl.countByNoteId(noteId);
        if (count == 0){
            throw new GeneralException("노트를 찾을 수 없습니다", 404);
        }
        else{
            NoteDto noteDto = noteRepositoryImpl.findByNoteId(noteId);
            if (noteDto.getUserId() != nowUser.getUserId()){
                throw new GeneralException("개인 단어장 조회 권한이 없습니다.", 403);
            }
        }
        NoteWordDto noteWordDto = noteRepositoryImpl.findNoteWord(noteId);
        if (noteWordDto.getWordDtoList().isEmpty()){
            throw new GeneralException("해당 단어장이 비어 있습니다", 404);
        }
        return noteWordDto;
    }

    public String createNoteWord(int noteId, List<Integer> wordIdList) {
        int count = noteRepositoryImpl.countByNoteId(noteId);

        if (wordIdList == null || wordIdList.isEmpty()){
            throw new GeneralException("추가할 단어를 선택하지 않았습니다.", 400);
        }

        if (count == 0){
            throw new GeneralException("노트를 찾을 수 없습니다", 404);
        }
        else{
            NoteDto noteDto = noteRepositoryImpl.findByNoteId(noteId);
            NoteWordDto noteWordDto = noteRepositoryImpl.findNoteWord(noteId);

            if (nowUser.getUserId() != noteDto.getUserId()){
                throw new GeneralException("단어 추가 권한이 없습니다.", 403);
            }
            for(int x : wordIdList){
                for(WordDto y : noteWordDto.getWordDtoList()){
                    if (x == y.getWordId()){
                        throw new GeneralException("중복된 단어를 선택했습니다.", 400);
                    }
                }
            }
        }
        return noteRepositoryImpl.createNoteWord(noteId, wordIdList);
    }

    public String deleteNoteWord(int noteId, List<Integer> wordIdList) {
        int count = noteRepositoryImpl.countByNoteId(noteId);
        if (count == 0){
            throw new GeneralException("노트를 찾을 수 없습니다", 404);
        }

        if (wordIdList == null || wordIdList.isEmpty()){
            throw new GeneralException("삭제할 단어를 선택하지 않았습니다.", 400);
        }
        else{
            NoteDto noteDto = noteRepositoryImpl.findByNoteId(noteId);
            NoteWordDto noteWordDto = noteRepositoryImpl.findNoteWord(noteId);

            if (nowUser.getUserId() != noteDto.getUserId()){
                throw new GeneralException("단어 추가 권한이 없습니다.", 403);
            }
            for(int x : wordIdList){
                boolean check = false;
                for(WordDto y : noteWordDto.getWordDtoList()){
                    if (x == y.getWordId()){
                        check = true;
                        break;
                    }
                }
                if (!check){
                    throw new GeneralException("단어장에 삭제하려는 단어가 없습니다", 403);
                }
            }
        }
        return noteRepositoryImpl.deleteNoteWord(noteId, wordIdList);
    }
}
