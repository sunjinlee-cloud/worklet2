package com.project2.worklet.calendarService;

import com.project2.worklet.component.CalendarVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CalendarService {



    List<CalendarVO> getAllEvent(String userId);

    //즐겨찾기 추가,삭제
    void addFavorite(int empSeqNo, String userId) ;
    void removeFavorite(int empSeqNo, String userId);

    List<Integer> getFavoriteEmpSeqNos(String userId);


}
