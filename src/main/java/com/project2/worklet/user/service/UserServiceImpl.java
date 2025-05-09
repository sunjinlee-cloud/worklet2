package com.project2.worklet.user.service;



import com.project2.worklet.component.*;

import com.project2.worklet.jobPostingService.JobPostingMapper;
import com.project2.worklet.util_interceptor.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public int insertUser(UserVO user) {
        return userMapper.insertUser(user);
    }

    @Override
    public UserVO loginUser(Map<String, String> paramMap) {
        String userId = paramMap.get("userId");
        String userPw = paramMap.get("userPw");

        return userMapper.loginUser(userId, userPw);
    }

    @Override
    public UserVO getUserById(String userId) {
        return userMapper.getUserById(userId);
    }


    @Override
    public boolean existsByUserId(String userId) {
        int count = userMapper.existsByUserId(userId);
        return count > 0;
    }

    @Override
    public UserVO findUserByUserId(String email, String phone) {
        return userMapper.findUserByUserId(email, phone);
    }

    @Override
    public UserVO findUserByUserPw(String userId, String userEmail) {
        return userMapper.findUserByUserPw(userId, userEmail);
    }

    @Override
    public int updatePw(UserVO user) {
        return userMapper.updatePw(user);
    }

    @Override
    public int insertEdu(EduVO edu) {
        return userMapper.insertEdu(edu);
    }

    @Override
    public int updateEdu(EduVO edu) {
        return userMapper.updateEdu(edu);
    }

    @Override
    public int insertCareer(CareerVO career) {
        return userMapper.insertCareer(career);
    }

    @Override
    public int updateCareer(CareerVO career) {
        return userMapper.updateCareer(career);
    }

    @Override
    public List<EduVO> getUserEducation(int userNum, Long resumeId) {
        return userMapper.getUserEducation(userNum, resumeId);
    }

    @Override
    public List<CareerVO> getUserCareer(int userNum) {
        return userMapper.getUserCareer(userNum);
    }

    @Override
    public List<LicenseVO> getUserLicenses(int userNum) {
        return userMapper.getUserLicenses(userNum);
    }

    @Override
    public int deleteEducation(Long educationId) {
        return userMapper.deleteEducation(educationId);
    }

    @Override
    public int deleteCareer(Long careerId) {
        return userMapper.deleteCareer(careerId);
    }

    @Override
    public int updateUser(UserVO user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int insertLicense(LicenseVO license) {
        return userMapper.insertLicense(license);
    }

    @Override
    public int updateLicense(LicenseVO license) {
        return userMapper.updateLicense(license);
    }

    @Override
    public int deleteLicense(Long licenseId) {
        return userMapper.deleteLicense(licenseId);
    }

    @Override
    public List<JobPostingVO2> getScrappedJob(String userId, Criteria cri) {
        return userMapper.getScrappedJob(userId, cri);
    }

    @Override
    public List<JobPostingVO2> getRecommendedJob(List<String> preferredJobTypes, Criteria cri) {
        return userMapper.getRecommendedJob(preferredJobTypes, cri);
    }

}
