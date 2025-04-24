package com.project2.worklet.user.service;


import com.project2.worklet.component.UserVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface UserMapper {
    int insertUser(UserVO user);
    UserVO loginUser(@Param("userId") String userId, @Param("userPw") String userPw);
    UserVO getUserById(String userId);
    int existsByUserId(String userId);
    // 아이디 찾기
    UserVO findUserByUserId(@Param("userEmail") String userEmail, @Param("userPhone") String userPhone);

}
