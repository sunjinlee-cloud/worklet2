package com.project2.worklet.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareerVO {

    private int userNum;///
    private Long careerId;
    private String companyName;///
    private String department;  // 부서///
    private String position;  // 직급///
    private LocalDate joinDate;  // 입사일///
    private LocalDate quitDate;  // 퇴사일///
    private String jobDescription;  // 주요업무///
    private LocalDate createdAt;  // 생성일
    private LocalDate updatedAt;  // 수정일

    private String formattedJoinDate;
    private String formattedQuitDate;
}
