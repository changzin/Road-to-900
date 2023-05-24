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
        // -1 비회원, 0은 유저, 1은 관리자
        this.role = role;
        // -1은 아직 테스트를 보지 않음, 티어는 0 - 1 - 2 순으로 올라감
        this.level = level;
        // 10, 20, 30 중 선택?
        this.dailyNoteNum = dailyNoteNum;
    }
}
