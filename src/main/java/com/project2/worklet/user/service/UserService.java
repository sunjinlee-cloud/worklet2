package com.project2.worklet.user.service;


import com.project2.worklet.component.UserVO;


import java.util.Map;

public interface UserService {

    // 회원가입
    int insertUser(UserVO user);

    // 로그인
    UserVO loginUser(Map<String, String> paramMap);

    UserVO getUserById(String userId);

    // 아이디 중복체크
    boolean existsByUserId(String userId);


    // 아이디 찾기
    UserVO findUserByUserId(String email, String phone);

}
