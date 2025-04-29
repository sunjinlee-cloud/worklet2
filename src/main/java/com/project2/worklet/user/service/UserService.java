package com.project2.worklet.user.service;


import com.project2.worklet.component.CareerVO;
import com.project2.worklet.component.EduVO;
import com.project2.worklet.component.UserVO;


import java.util.List;
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

    // 비밀번호 찾기
    UserVO findUserByUserPw(String userId, String userEmail);

    // 비밀번호 바꾸기
    int updatePw(UserVO user);

    // 학력 추가
    int insertEdu(EduVO edu);

    // 학력 수정
    int updateEdu(EduVO edu);

    // 경력 추가
    int insertCareer(CareerVO career);

    // 경력 수정
    int updateCareer(CareerVO career);

    // 학력 조회 추가
    List<EduVO> getUserEducation(int userNum);

    // 경력 조회 추가
    List<CareerVO> getUserCareer(int userNum);


    // 학력 삭제
    int deleteEducation(Long educationId);

    // 경력 삭제
    int deleteCareer(Long careerId);

}
