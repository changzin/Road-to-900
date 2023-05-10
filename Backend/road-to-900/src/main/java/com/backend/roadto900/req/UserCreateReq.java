package com.backend.roadto900.req;
import lombok.Getter;

@Getter
public class UserCreateReq {
    private String uid;
    private String userName;
    private String password;
    private String passwordCheck;
}
