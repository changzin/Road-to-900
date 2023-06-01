package com.backend.roadto900.repository;

import com.backend.roadto900.dto.UserDto;
import com.backend.roadto900.req.UserLoginReq;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<UserDto> userRowMapper = new RowMapper<UserDto>() {
        @Override
        public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserDto(
                rs.getInt("user_id"),
                rs.getString("uid"),
                rs.getString("user_name"),
                rs.getString("password"),
                rs.getInt("role"),
                rs.getInt("level"),
                rs.getInt("daily_note_num")
            );
        }
    };

    @Override
    public UserDto save(UserDto userDto) {
        jdbcTemplate.execute("INSERT INTO user(uid, user_name, password, role, level, daily_note_num) VALUES('"+
                userDto.getUid()+
                "','" + userDto.getUserName()+
                "','" + userDto.getPassword()+
                "'," + userDto.getRole()+
                "," + userDto.getLevel()+
                "," + userDto.getDailyNoteNum()+
                ")"
        );

        UserDto newUserDto = jdbcTemplate.queryForObject("SELECT * FROM user WHERE uid ='" +
                userDto.getUid() + "'", userRowMapper);
        return newUserDto;
    }

    @Override
    public UserDto login(UserLoginReq userLoginReq) {
        UserDto userDto = jdbcTemplate.queryForObject("SELECT * FROM user WHERE uid ='" +
                userLoginReq.getUid() +"'"
                , userRowMapper);
        return userDto;
    }

    @Override
    public int countByUid(String uid) {
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM user WHERE uid='"
                + uid + "'"
                , Integer.class);
        return count;
    }

    @Override
    public void setDailyNoteNum(int userId, int dailyNoteNum) {
        jdbcTemplate.execute("UPDATE user SET daily_note_num="+
                dailyNoteNum+
                " where user_id=" + userId);
    }

    @Override
    public int updateUserLevel(int userId, int questions, int answer) {
        int newLevel;

        double answerRate = (Double.parseDouble(Integer.toString(answer))) / (Double.parseDouble(Integer.toString(questions)));
        answerRate = Integer.parseInt(Double.toString(answerRate));

        if (answerRate >= 80){
            newLevel = 2;
        }
        else if (answerRate >= 40){
            newLevel = 1;
        }
        else {
            newLevel = 0;
        }

        jdbcTemplate.execute("UPDATE user SET level="+
                newLevel+
                " where user_id=" + userId);
        return newLevel;
    }
}
