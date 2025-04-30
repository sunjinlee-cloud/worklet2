package com.project2.worklet.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseVO {

    private Long licenseId;          // 자격증 PK
    private int userNum;             // 회원 번호 (FK)
    private String licenseName;      // 자격증 이름
    private String licenseOrg;       // 발급 기관
    private LocalDate acquisition;   // 취득일
    private LocalDate expiration;

    private String formattedAcquisition;
    private String formattedExpiration;
}
