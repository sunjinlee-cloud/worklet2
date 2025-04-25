package com.project2.worklet.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalendarVO {

    private String empSeqNo;
    private String empWantedTitle;
    private String empBusiNm;
    private String empWantedStdt;
    private String empWantedEndt;
    private String empWantedTypeNm;
    private String regLogImgNm;
    private String empWantedHomepgDetail;

    public String getTitle() {
        return empWantedTitle;
    }

    public String getStart() {
        if(empWantedStdt != null && empWantedStdt.length() == 8) {
            return empWantedStdt.substring(0, 4) + "-" + empWantedStdt.substring(4, 6) + "-" + empWantedStdt.substring(6, 8);
        }
        return null;
    }

}
