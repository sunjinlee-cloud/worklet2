package com.project2.worklet.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class JobPostingDetailVO {
    private Integer jpdno;
    private int empSeqno;
    private String empRecrNm;
    private String empWantedCareerNm;
    private String workRegionNm;
    private int jobsCd;
    private String jobsCdKorNm;
}
