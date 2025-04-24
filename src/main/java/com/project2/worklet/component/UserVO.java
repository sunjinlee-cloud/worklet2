
package com.project2.worklet.component;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private int userNum;
    private String userId;
    private String userPw;
    private String userName;
    private String userGender;
    private String userPhone;
    private String userEmail;
    private String userAddress;
    private String userBirthday;
    private String wantJobNum;
    private String wantJobType1;
    private String wantJobType2;
    private String wantJobType3;
    private String[] wantJobType;
    private int wantJobWorkexp;
    private String wantJobWorkexpHowlong;
    private LocalDateTime joinDate;
}
