package com.backend.roadto900.repository;

import com.backend.roadto900.dto.WordAddDto;
import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.exception.GeneralException;
import com.backend.roadto900.req.WordAddDeleteReq;
import com.backend.roadto900.req.WordAskReq;
import com.backend.roadto900.req.WordDeleteReq;
import com.backend.roadto900.req.WordInsertReq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class WordRepositoryImpl implements WordRepository{
    private final JdbcTemplate jdbcTemplate;
    private static Map<Long, WordDto> store = new HashMap<>();

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

    //word_add 테이블을 위한 RowMapper
    private final RowMapper<WordAddDto> wordAddDtoRowMapper = new RowMapper<WordAddDto>() {
        @Override
        public WordAddDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new WordAddDto(
                    rs.getInt("word_add_id"),
                    rs.getString("word_add_spell")
            );
        }

    };


    @Override
    @Transactional(rollbackFor = Exception.class)
    public WordDto insertWord(WordInsertReq wordInsertReq) {
            String sql = "INSERT INTO word (spell, mean) VALUES (?, ?)";
            jdbcTemplate.update(sql, wordInsertReq.getSpell(), wordInsertReq.getMean());
            WordDto newWordDto = jdbcTemplate.queryForObject("SELECT * FROM word WHERE spell = ? AND mean = ?", new Object[]{wordInsertReq.getSpell(), wordInsertReq.getMean()}, wordRowMapper);
            return newWordDto;
    }


    @Override
    public List<WordDto> findAll() {
        List<WordDto> wordDtoList = jdbcTemplate.query("SELECT word_id,spell, mean FROM word", wordRowMapper);
        return wordDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWord(int deleteWordId) {
        int count = jdbcTemplate.queryForObject("SELECT count(*) FROM word WHERE word_Id="+deleteWordId, Integer.class);
        if (count == 0){
            throw new GeneralException("삭제할 단어를 찾을 수 없습니다", 404);
        }
        jdbcTemplate.update("DELETE FROM WORD WHERE word_id= ?", deleteWordId);
    }

    @Override
    public List<WordDto> searchWord(String spell) {
        List<WordDto> wordDto = jdbcTemplate.query("SELECT * FROM word WHERE spell = ?", new Object[]{spell}, wordRowMapper);
        return wordDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WordDto askWord(WordAskReq wordAskReq) {
        jdbcTemplate.update("INSERT INTO word_add (word_add_spell) VALUES (?)",wordAskReq.getSpell());
        return null;
    }

    @Override
    public List<WordAddDto> findAskWord() {
        List<WordAddDto> wordAddDtoList = jdbcTemplate.query("SELECT * FROM word_add",wordAddDtoRowMapper);
        System.out.println("wordAddDtoList.size() = " + wordAddDtoList.size());
        return wordAddDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAskWord(int deleteAskWordId){
        int count = jdbcTemplate.queryForObject("SELECT count(*) FROM word_add WHERE word_add_Id="+deleteAskWordId, Integer.class);
        if (count == 0){
            throw new GeneralException("삭제할 단어 요청을 찾을 수 없습니다", 404);
        }
        jdbcTemplate.update("DELETE FROM word_add WHERE word_add_id= ?", deleteAskWordId);
    }
}
