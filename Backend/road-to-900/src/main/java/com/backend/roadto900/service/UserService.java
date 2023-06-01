package com.backend.roadto900.service;

import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.UserDto;
import com.backend.roadto900.exception.GeneralException;
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

    public UserDto join(UserCreateReq userCreateReq) {
        // 회원가입 권한 예외처리
        if (nowUser.getRole() != -1){
            throw new GeneralException("회원가입 권한이 없습니다.", 403);
        }
        // 아이디 중복체크 예외처리
        duplicateUidCheck(userCreateReq.getUid());
        // 비밀번호 두번 쓴 거 잘 맞췄는지 예외처리
        passwordCheck(userCreateReq.getPassword(), userCreateReq.getPasswordCheck());

        // 이름, 아이디, 비밀번호 길이 체크
        if (userCreateReq.getUserName().length() < 1 || userCreateReq.getUserName().length() > 10){
            throw new GeneralException("잘못된 이름 형식입니다.", 400);
        }
        if (userCreateReq.getUid().length() < 7 || userCreateReq.getUid().length() > 15){
            throw new GeneralException("잘못된 아이디 형식입니다", 400);
        }
        if (userCreateReq.getPassword().length() < 7 || userCreateReq.getPassword().length() > 15){
            throw new GeneralException("잘못된 비밀번호 형식입니다", 400);
        }

        UserDto userDto = new UserDto(userCreateReq.getUid(), userCreateReq.getUserName(), userCreateReq.getPassword(), 0, 0, 20);
        return userRepository.save(userDto);
    }

    public NowUser login(UserLoginReq userLoginReq) {
        // 로그인 권한 예외처리
        if (nowUser.getRole() != -1){
            throw new GeneralException("로그인 권한이 없습니다.", 403);
        }
        // 유저가 존재하는지 예외처리
        existUidCheck(userLoginReq.getUid());
        // uid와 비밀번호가 일치하지 않음
        UserDto userDto = userRepository.login(userLoginReq);

        passwordCheck(userDto.getPassword(), userLoginReq.getPassword());

        nowUser.loginUpdate(userDto);
        return nowUser;
    }

    public String logout(){
        // 로그아웃 권한 예외처리
        if (nowUser.getRole() == -1){
            throw new GeneralException("로그아웃 권한이 없습니다.", 403);
        }
        nowUser.Logout();
        return "로그아웃 완료 되었습니다.";
    }

    public NowUser findNowUser(){
        return nowUser;
    }

    public String setDailyNoteNum(int dailyNoteNum){
        // 로그인 권한 예외처리
        if (nowUser.getRole() != 0){
            throw new GeneralException("오늘의 단어장 권한이 없습니다", 403);
        }

        nowUser.setDailyNoteNum(dailyNoteNum);
        userRepository.setDailyNoteNum(nowUser.getUserId(), dailyNoteNum);
        return "변경이 완료 되었습니다.";
    }

    private void duplicateUidCheck(String uid){
        int count = userRepository.countByUid(uid);
        if (count != 0){
            throw new GeneralException("중복된 ID입니다.", 400);
        }
    }

    private void passwordCheck(String password, String passwordCheck){
        if (!password.equals(passwordCheck)){
            throw new GeneralException("비밀번호가 일치하지 않습니다.", 400);
        }
    }

    private void existUidCheck(String uid){
        int count = userRepository.countByUid(uid);
        if (count != 1){
            throw new GeneralException("일치하는 ID를 찾을 수 없습니다.", 404);
        }
    }

    public String updateUserLevel(int questions, int answer) {
        if (nowUser.getRole() != 0){
            throw new GeneralException("시험 결과를 반영할 권한이 없습니다.", 403);
        }
        existUidCheck(nowUser.getUid());
        if (questions == 0){
            throw new GeneralException("전체 문제 수가 0. 잘못된 입력입니다.", 400);
        }
        int newLevel = userRepository.updateUserLevel(nowUser.getUserId(), questions, answer);
        nowUser.setLevel(newLevel);
        return "유저 레벨 변경 완료 하였습니다.";
    }
}
