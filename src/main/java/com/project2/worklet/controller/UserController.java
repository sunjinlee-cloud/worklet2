package com.project2.worklet.controller;

import com.project2.worklet.component.CareerVO;
import com.project2.worklet.component.EduVO;
import com.project2.worklet.component.UserVO;

import com.project2.worklet.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;


    @GetMapping("/regist")
    public String showRegistForm() {
        return "User/regist";  // 회원가입 폼을 보여줌
    }

    @PostMapping("/regist")
    public String regist(UserVO user, @RequestParam("wantJobType") String[] wantJobTypes) {

        log.info( user.toString() );
        user.setJoinDate(LocalDateTime.now());

        user.setWantJobType1(wantJobTypes.length > 0 ? wantJobTypes[0] : null);
        user.setWantJobType2(wantJobTypes.length > 1 ? wantJobTypes[1] : null);
        user.setWantJobType3(wantJobTypes.length > 2 ? wantJobTypes[2] : null);

        int result = userService.insertUser(user);
        if(result == 1) {
            log.info( user.toString() );
        } else {
            log.info( "실패");
        }

        return "redirect:/user/login";  // 로그인 페이지로 리다이렉트
    }


    @GetMapping("/login")
    public String login() {
        return "User/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId,
                        @RequestParam String userPw,
                        Model model,
                        HttpSession session) {


        // paramMap 만들어서 서비스로 전달
        Map<String, String> paramMap = Map.of(
                "userId", userId,
                "userPw", userPw
        );

        // 서비스 호출
        UserVO user = userService.loginUser(paramMap);

        if (user != null) {
            model.addAttribute("user", user);
            session.setAttribute("loginUser", user);
            System.out.println(">>> login – session: " + session.getId());
            System.out.println(">>> loginUser: " + session.getAttribute("loginUser"));
            return "redirect:/Board/mainPage";// 로그인 성공 시
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "User/login"; // 실패 시
        }
    }


    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {

        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        if(loginUser != null) {

            UserVO fullUser = userService.getUserById(loginUser.getUserId());

            List<String> wantJobTypes = new ArrayList<>();
            if (fullUser.getWantJobType1() != null) wantJobTypes.add(fullUser.getWantJobType1());
            if (fullUser.getWantJobType2() != null) wantJobTypes.add(fullUser.getWantJobType2());
            if (fullUser.getWantJobType3() != null) wantJobTypes.add(fullUser.getWantJobType3());
            fullUser.setWantJobType(wantJobTypes.toArray(new String[0]));

            model.addAttribute("userVO", fullUser);
            return "User/mypage";
        }else {
            return "redirect:/User/login";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/Board/mainPage"; // 홈페이지로 리다이렉트
    }

    @GetMapping("/resume")
    public String resume(HttpSession session, Model model) {

        UserVO vo = (UserVO) session.getAttribute("loginUser");

        if(vo != null){
            UserVO fullUser = userService.getUserById(vo.getUserId());

            if (fullUser.getEducationList() == null) {
                fullUser.setEducationList(new ArrayList<>());
            }

            if (fullUser.getCareerList() == null) {
                fullUser.setCareerList(new ArrayList<>());
            }


            // String을 LocalDate로 변환
            // 생일이 있을 경우 만나이 계산
            if (fullUser.getUserBirthday() != null && !fullUser.getUserBirthday().isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate birthday = LocalDate.parse(fullUser.getUserBirthday(), formatter);
                    int age = Period.between(birthday, LocalDate.now()).getYears();

                    model.addAttribute("userBirthday", birthday); // 생일 자체도 넘기고
                    model.addAttribute("age", age); // 만나이도 넘김
                } catch (Exception e) {
                    e.printStackTrace(); // 파싱 실패 시 로그
                }
            }

            model.addAttribute("userVO", fullUser);
            return "User/resume";
        }else{
            return "redirect:/User/login";
        }


    }


    @GetMapping("/id")
    public String id() {
        return "User/id";
    }

    @GetMapping("/pw")
    public String pw() {
        return "User/pw";
    }



    @GetMapping("/check-id")
    @ResponseBody
    public String checkIdDuplicate(@RequestParam String userId) {
        boolean isDuplicate = userService.existsByUserId(userId);

        System.out.println("아이디 중복 여부: " + isDuplicate);
        return isDuplicate ? "duplicate" : "available";
    }

    @GetMapping("/idSearch")
    public String idSearch(Model model) {


        UserVO userVO = new UserVO();
        model.addAttribute("userVO", userVO);

        return "User/idSearch";
    }

    @GetMapping("/pwSearch")
    public String pwSearch(Model model) {
        String userId = (String) model.getAttribute("userId");  // Model에서 userId 가져오기

        if (userId != null) {
            // userId를 사용하여 추가 로직을 처리할 수 있습니다.
        }

        return "User/pwSearch";
    }

    @GetMapping("/pwEdit")
    public String pwEditForm(@RequestParam("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/pwEdit";
    }

    @PostMapping("/pwEdit")
    public String pwEdit(@RequestParam String userId,
                         @RequestParam String newPassword) {

        System.out.println("비밀번호 변경 시 userId: " + userId);

        UserVO vo = new UserVO();

        vo.setUserId(userId);
        vo.setUserPw(newPassword);

        int result = userService.updatePw(vo);
        System.out.println("비밀번호 변경 결과: " + result);
        if(result == 1) {
            return "redirect:/user/login";
        }else{
            return "redirect:/user/pwEdit";
        }


    }

    @GetMapping("/pwOk")
    public String pwOk() {
        return "User/pwOk";
    }

    @PostMapping("/pwOk")
    public String pwOk(@RequestParam("userId") String userId,
                       @RequestParam("userEmail") String userEmail,
                       Model model) {

        UserVO vo = userService.findUserByUserPw(userId, userEmail);

        if(vo != null){
            model.addAttribute("userVO", vo);
            model.addAttribute("userId", userId);
            return "User/pwSearch";
        }else{
            model.addAttribute("errorMessage", "비밀번호를 찾을 수 없습니다.");
            return "User/pwOk";
        }


    }

    @GetMapping("/idOk")
    public String idOk() {
        return "User/idOk";
    }

    @PostMapping("/idOk")
    public String idOk(@RequestParam("userEmail") String email,
                       @RequestParam("userPhone") String phone,
                       Model model) {

        UserVO vo = userService.findUserByUserId(email, phone);

        if (vo != null) {

            model.addAttribute("userVO", vo);
        } else {

            model.addAttribute("errorMessage", "아이디를 찾을 수 없습니다.");
        }
        return "User/idSearch";
    }

    // 학력 추가 처리
    @PostMapping("/updateEdu")
    public String updateEducation(@RequestParam("userNum") String userNum,
                                  @RequestParam("schoolName") String schoolName,
                                  @RequestParam("major") String major,
                                  @RequestParam("part") String part,
                                  @RequestParam("degreeType") String degreeType,
                                  @RequestParam("graduationStatus") String graduationStatus,
                                  @RequestParam("graduationDate") String graduationDate) {

        // userNum이 숫자인지 확인
        int userNumInt = 0;
        if (userNum != null && userNum.matches("\\d+")) {  // 숫자만 포함된 문자열인지 확인
            userNumInt = Integer.parseInt(userNum);  // 숫자로 변환
        } else {
            log.error("유효하지 않은 userNum 값: " + userNum);
            return "redirect:/error";  // 숫자가 아닌 값이 들어오면 에러 페이지로 리디렉션
        }

        // graduationDate를 String에서 LocalDate로 변환
        LocalDate graduationDateLocal = null;
        if (graduationDate != null && !graduationDate.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                graduationDateLocal = LocalDate.parse(graduationDate, formatter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // EduVO 객체에 폼에서 받은 데이터를 설정
        EduVO eduVO = new EduVO();
        eduVO.setUserNum(userNumInt);  // ✅ 여기 userNumInt로
        eduVO.setSchoolName(schoolName);
        eduVO.setMajor(major);
        eduVO.setPart(part);
        eduVO.setDegreeType(degreeType);
        eduVO.setGraduationStatus(graduationStatus);
        eduVO.setGraduationDate(graduationDateLocal);

        // 데이터 삽입
        int result = userService.insertEdu(eduVO);

        // 결과 처리
        if (result > 0) {
            log.info("학력 추가 성공");
        } else {
            log.error("학력 추가 실패");
        }

        return "redirect:/user/resume"; // ✅ 소문자로
    }


    // 학력 수정 처리
    @PostMapping("/editEdu")
    public String editEducation(@ModelAttribute EduVO eduVO) {
        int result = userService.updateEdu(eduVO);
        if (result > 0) {
            log.info("학력 수정 성공");
        } else {
            log.error("학력 수정 실패");
        }
        return "redirect:/user/resume"; // 수정 후 프로필 페이지로 리디렉션
    }

    // 경력 추가 처리
    @PostMapping("/updateCareer")
    public String updateCareer(@ModelAttribute CareerVO careerVO) {
        int result = userService.insertCareer(careerVO);
        if (result > 0) {
            log.info("경력 추가 성공");
        } else {
            log.error("경력 추가 실패");
        }
        return "redirect:/user/resume"; // 수정 후 프로필 페이지로 리디렉션
    }

    // 경력 수정 처리
    @PostMapping("/editCareer")
    public String editCareer(@ModelAttribute CareerVO careerVO) {
        int result = userService.updateCareer(careerVO);
        if (result > 0) {
            log.info("경력 수정 성공");
        } else {
            log.error("경력 수정 실패");
        }
        return "redirect:/user/resume"; // 수정 후 프로필 페이지로 리디렉션
    }




}
