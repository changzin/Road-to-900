package com.backend.roadto900.repository;

import com.backend.roadto900.dto.WordDto;
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

    System.out.println("INSERT INTO word(word_id, spell, mean) VALUES('" +
            wordInsertReq.getSpell() +
            "','" + wordInsertReq.getMean() +
            "')");
    jdbcTemplate.update("INSERT INTO word(spell, mean) VALUES(?, ?)", wordInsertReq.getSpell(), wordInsertReq.getMean());
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

}
