package com.backend.roadto900.repository;

import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.NoteDto;
import com.backend.roadto900.dto.NoteWordDto;
import com.backend.roadto900.dto.UserDto;
import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoteRepositoryImpl implements  NoteRepository{

    private final JdbcTemplate jdbcTemplate;
    private final NowUser nowUser;

    private final RowMapper<NoteDto> noteRowMapper = new RowMapper<NoteDto>() {
        @Override
        public NoteDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new NoteDto(
                    rs.getInt("note_id"),
                    rs.getInt("user_id"),
                    rs.getString("note_name")
            );
        }
    };
    private final RowMapper<WordDto> wordRowMapper = new RowMapper<WordDto>() {
        @Override
        public WordDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new WordDto(
                    rs.getInt("word_id"),
                    rs.getString("spell"),
                    rs.getString("mean")
            );
        }
    };

    @Override
    public List<NoteDto> findAll() {
        List<NoteDto> noteDtoList = jdbcTemplate.query("SELECT * FROM note WHERE user_id="+
                nowUser.getUserId(),noteRowMapper);
        return noteDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(String noteName) {
        jdbcTemplate.execute("INSERT INTO NOTE(user_id, note_name) VALUES("+
                nowUser.getUserId() +
                ",'" + noteName + "')");
        return "개인 단어장 추가가 완료 되었습니다.";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(int noteId) {
        jdbcTemplate.execute("DELETE FROM NOTE WHERE note_id="+noteId);
    }

    @Override
    public NoteWordDto findNoteWord(int noteId) {
        List<WordDto> wordDtoList = jdbcTemplate.query("SELECT DISTINCT word.word_id, word.spell, word.mean FROM word, note_word" +
                        " WHERE note_word.word_id = word.word_id AND note_word.note_id="+noteId, wordRowMapper);
        NoteWordDto noteWordDto = new NoteWordDto(noteId, findByNoteId(noteId).getNoteName(), wordDtoList);
        return noteWordDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createNoteWord(int noteId, List<Integer> wordIdList) {
        for(int wordId : wordIdList){
            jdbcTemplate.execute("INSERT INTO note_word(note_id, word_id) VALUES(" + noteId + ", " + wordId + ")");
        }
        return "개인 단어장 단어 추가가 완료 되었습니다.";
    }

    @Override
    public String deleteNoteWord(int noteId, List<Integer> wordIdList) {
        for(int wordId : wordIdList){
            jdbcTemplate.execute("DELETE FROM note_word WHERE note_id=" + noteId + " AND word_id=" + wordId);
        }
        return "개인 단어장 단어 삭제가 완료 되었습니다.";
    }

    @Override
    public int countByNoteId(int noteId) {
        int count = jdbcTemplate.queryForObject("SELECT count(*) FROM note WHERE note_Id="+noteId, Integer.class);
        return count;
    }

    @Override
    public int countByNoteName(String noteName) {
        int count = jdbcTemplate.queryForObject("SELECT count(*) FROM note WHERE note_name='"+noteName+"' AND user_id=" + nowUser.getUserId(), Integer.class);
        return count;
    }

    @Override
    public NoteDto findByNoteId(int noteId) {
        NoteDto noteDto = jdbcTemplate.queryForObject("SELECT * FROM note WHERE note_id="+
                noteId, noteRowMapper);

        return noteDto;
    }
}
