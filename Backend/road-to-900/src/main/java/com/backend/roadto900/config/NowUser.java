package com.backend.roadto900.config;

import com.backend.roadto900.dto.UserDto;
import lombok.Getter;

@Getter
public class NowUser {

    private int userId;
    private String uid;
    private String userName;
    private String password;
    private int role;
    private int level;
    private int dailyNoteNum;

    public NowUser(){
        // 전부 쓰이지 않을 값으로 변경
        userId = -1;
        uid = "register";
        userName ="register";
        password = "register";
        role = -2;
        level = -2;
        dailyNoteNum = -2;
    }

    public void Logout(){
        // 전부 쓰이지 않을 값으로 변경
        userId = -1;
        uid = "register";
        userName ="register";
        password = "register";
        role = -1;
        level = -1;
        dailyNoteNum = -1;
    }

    public void loginUpdate(UserDto userDto){
        this.userId = userDto.getUserId();
        this.uid = userDto.getUid();
        this.userName = userDto.getUserName();
        this.password = userDto.getPassword();
        this.role = userDto.getRole();
        this.level = userDto.getLevel();
        this.dailyNoteNum = userDto.getDailyNoteNum();
    }

    public void setDailyNoteNum(int dailyNoteNum){
        this.dailyNoteNum = dailyNoteNum;
    }
}
