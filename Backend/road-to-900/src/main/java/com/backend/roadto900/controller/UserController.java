package com.backend.roadto900.controller;

import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.UserDto;
import com.backend.roadto900.req.UserCreateReq;
import com.backend.roadto900.req.UserLoginReq;
import com.backend.roadto900.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login/signUp")
    public ResponseEntity signUp(@RequestBody UserCreateReq userCreateReq) {
        UserDto userDto;

        try {
            userDto = userService.join(userCreateReq);
        }
        catch (IllegalStateException e)
        {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.status(201).body(userDto);
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginReq userLoginReq){
        NowUser nowUser;
        try {
            nowUser = userService.login(userLoginReq);
        }
        catch (IllegalStateException e)
        {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.status(201).body(nowUser);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(){
        NowUser nowUser = userService.logout();

        return ResponseEntity.status(201).body(nowUser);
    }

    @GetMapping("/myPage")
    public ResponseEntity findNowUser(){
        NowUser nowUser = userService.findNowUser();

        return ResponseEntity.status(201).body(nowUser);
    }
}
