package com.backend.roadto900.repository;

import com.backend.roadto900.dto.WordAddDto;
import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.req.WordAddDeleteReq;
import com.backend.roadto900.req.WordAskReq;
import com.backend.roadto900.req.WordDeleteReq;
import com.backend.roadto900.req.WordInsertReq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
    public List<WordDto> deleteWord(WordDeleteReq deleteWordReq) {
        jdbcTemplate.update("DELETE FROM WORD WHERE word_id= ?", deleteWordReq.getWordId());
        return findAll();
    }

    @Override
    public List<WordDto> searchWord(String spell) {
        List<WordDto> wordDto = jdbcTemplate.query("SELECT * FROM word WHERE spell = ?", new Object[]{spell}, wordRowMapper);
        return wordDto;
    }

    @Override
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
    public List<WordAddDto> deleteAskWord(WordAddDeleteReq wordAddDeleteReq){

//        * 배열로 들어온 값을 가져와서 for문으로 하고 싶은데 안된다.
        List<Integer> item = wordAddDeleteReq.getWordAddId();

        for (Integer wordId: item) {
            System.out.println("wordAddId = " + wordId);
        }
//      jdbcTemplate.update("DELETE FROM word_add where id = ?", wordAddDeleteReq.getWordAddId()));
        return findAskWord();
    }
}
