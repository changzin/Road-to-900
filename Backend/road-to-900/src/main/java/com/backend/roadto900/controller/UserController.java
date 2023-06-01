package com.backend.roadto900.controller;

import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.QuestionDto;
import com.backend.roadto900.dto.UserDto;
import com.backend.roadto900.req.UserCreateReq;
import com.backend.roadto900.req.UserLoginReq;
import com.backend.roadto900.service.TestService;
import com.backend.roadto900.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TestService testService;
    @PostMapping("/login/signUp")
    public ResponseEntity signUp(@RequestBody UserCreateReq userCreateReq) {
        UserDto userDto;
        userDto = userService.join(userCreateReq);
        return ResponseEntity.status(201).body(userDto);
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginReq userLoginReq){
        NowUser nowUser = userService.login(userLoginReq);
        return ResponseEntity.ok().body(nowUser);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(){
        String result = userService.logout();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/myPage")
    public ResponseEntity findNowUser(){
        NowUser nowUser = userService.findNowUser();
        return ResponseEntity.ok().body(nowUser);
    }

    @PostMapping("/dailyNoteSetting")
    public ResponseEntity setDailyNoteNum(@RequestParam int dailyNoteNum){
        String result = userService.setDailyNoteNum(dailyNoteNum);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/firstTest")
    public ResponseEntity getFirstTest(){
        List<QuestionDto> questionDtoList = testService.makeFirstTest();
        return ResponseEntity.ok().body(questionDtoList);
    }

    @PostMapping("/postScore")
    public ResponseEntity updateUserLevel(@RequestParam int questions, @RequestParam int answer){
        String result = userService.updateUserLevel(questions, answer);
        return ResponseEntity.ok().body(result);
    }
}
