package com.backend.roadto900.service;

import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.UserDto;
import com.backend.roadto900.repository.UserRepository;
import com.backend.roadto900.req.UserCreateReq;
import com.backend.roadto900.req.UserLoginReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final NowUser nowUser;

    public UserDto join(UserCreateReq userCreateReq) throws IllegalStateException{
        // 아이디 중복체크 예외처리
        duplicateUidCheck(userCreateReq.getUid());
        // 비밀번호 두번 쓴 거 잘 맞췄는지 예외처리
        passwordCheck(userCreateReq.getPassword(), userCreateReq.getPasswordCheck());

        UserDto userDto = new UserDto(userCreateReq.getUid(), userCreateReq.getUserName(), userCreateReq.getPassword(), 0, 0, 20);
        return userRepository.save(userDto);
    }

    public NowUser login(UserLoginReq userLoginReq) throws IllegalStateException{
        // 현재 로그아웃 상태인지 확인

        // 매칭되는 uid가 존재하지 않음
        existUidCheck(userLoginReq.getUid());
        // uid와 비밀번호가 일치하지 않음
        UserDto userDto = userRepository.login(userLoginReq);

        passwordCheck(userDto.getPassword(), userLoginReq.getPassword());

        nowUser.loginUpdate(userDto);
        return nowUser;
    }

    public NowUser logout(){
        nowUser.Logout();
        return nowUser;
    }

    public NowUser findNowUser(){
        return nowUser;
    }

    private void duplicateUidCheck(String uid){
        int count = userRepository.countByUid(uid);
        if (count != 0){
            throw new IllegalStateException("사용되고 있는 ID입니다.");
        }
    }

    private void passwordCheck(String password, String passwordCheck){
        if (!password.equals(passwordCheck)){
            throw new IllegalStateException("입력된 비밀번호가 올바르지 않습니다");
        }
    }

    private void existUidCheck(String uid){
        int count = userRepository.countByUid(uid);
        if (count != 1){
            throw new IllegalStateException("일치하는 ID가 존재하지 않습니다.");
        }
    }
}
