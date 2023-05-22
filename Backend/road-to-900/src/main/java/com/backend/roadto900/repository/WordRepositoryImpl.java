package com.backend.roadto900.repository;

import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.req.WordAskReq;
import com.backend.roadto900.req.WordDeleteReq;
import com.backend.roadto900.req.WordInsertReq;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public WordDto insertWord(WordInsertReq wordInsertReq) {
        String sql = "SELECT COUNT(*) FROM word WHERE spell = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, wordInsertReq.getSpell());

        if (count == 0) {
            sql = "INSERT INTO word (spell, mean) VALUES (?, ?)";
            jdbcTemplate.update(sql, wordInsertReq.getSpell(), wordInsertReq.getMean());
            WordDto newWordDto = jdbcTemplate.queryForObject("SELECT * FROM word WHERE spell = ? AND mean = ?", new Object[]{wordInsertReq.getSpell(), wordInsertReq.getMean()}, wordRowMapper);
            return newWordDto;
        } else {
            WordDto duplicateWordDto = new WordDto(-1, "Duplicate","이미 존재하는 단어 입니다.");
            return duplicateWordDto;
        }
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
    public WordDto searchWord(String spell) {
        WordDto wordDto = jdbcTemplate.queryForObject("SELECT * FROM word WHERE spell = ?", new Object[]{spell}, wordRowMapper);
        return wordDto;
    }

    @Override
    public WordDto askWord(WordAskReq wordAskReq) {
        jdbcTemplate.update("INSERT INTO word_add (word_add_spell) VALUES (?)",wordAskReq.getSpell());
        return null;
    }
}
