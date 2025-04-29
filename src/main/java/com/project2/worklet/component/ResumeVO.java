package com.project2.worklet.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResumeVO {


//    private String username;
//    private String userBirthday;
//    private String userAddress;
//    private String userEmail;
//    private String userPhone;
//    private String army;
//    private String resumeEduPeriod1;
//    private String resumeEduPeriod2;
//    private String resumeEduCont1;
//    private String resumeEduCont2;
//    private String resumeCarPeriod1;
//    private String resumeCarPeriod2;
//    private String resumeCarBusiName1;
//    private String resumeCarBusiName2;
//    private String resumeCarWorkCont1;
//    private String resumeCarWorkCont2;
//    private String resumeCertRegDate1;
//    private String resumeCertRegDate2;
//    private String resumeCertRegDate3;
//    private String resumeCertRegDate4;
//    private String resumeCertName1;
//    private String resumeCertName2;
//    private String resumeCertName3;
//    private String resumeCertName4;
//    private String resumeCertFrom1;
//    private String resumeCertFrom2;
//    private String resumeCertFrom3;
//    private String resumeCertFrom4;
    @JsonProperty("resumeEarlyLife")
    private String resumeEarlyLife;

    @JsonProperty("resumeStrengthWeakness")
    private String resumeStrengthWeakness;

    @JsonProperty("resumeApplyReason")
    private String resumeApplyReason;

    @JsonProperty("resumeApplyAfterDream")
    private String resumeApplyAfterDream;


}
