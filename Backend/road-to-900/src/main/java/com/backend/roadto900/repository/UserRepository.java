package com.backend.roadto900.repository;

import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.UserDto;
import com.backend.roadto900.req.UserCreateReq;
import com.backend.roadto900.req.UserLoginReq;

public interface UserRepository {
    UserDto save(UserDto userDto);
    UserDto login(UserLoginReq userLoginReq);

    int countByUid(String uid);

    void setDailyNoteNum(int userId, int dailyNoteNum);

    int updateUserLevel(int userId, int questions, int answer);
}
