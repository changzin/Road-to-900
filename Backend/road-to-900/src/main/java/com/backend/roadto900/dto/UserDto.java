package com.backend.roadto900.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private int userId;
    private String uid;
    private String userName;
    private String password;
    private int role;
    private int level;
    private int dailyNoteNum;

    public UserDto(String uid, String userName, String password, int role, int level, int dailyNoteNum) {
        this.uid = uid;
        this.userName = userName;
        this.password = password;
        // 0은 유저, 1은 관리자
        this.role = role;
        // 0은 아직 테스트를 보지 않음, 티어는 1 - 2 - 3 순으로 올라감
        this.level = level;
        // 디폴트 값 지정 (일단 20개로 지정)
        this.dailyNoteNum = dailyNoteNum;
    }
}
