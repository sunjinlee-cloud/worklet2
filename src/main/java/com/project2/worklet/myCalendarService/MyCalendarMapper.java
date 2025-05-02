package com.project2.worklet.myCalendarService;

import com.project2.worklet.component.CalendarVO;
import com.project2.worklet.component.MyCalendarVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyCalendarMapper {

    List<MyCalendarVO> getAllEvent();

    //달력버튼
    List<MyCalendarVO> getStartDayEvents(String userId);
    List<MyCalendarVO> getEndDayEvents(String userId);
    List<MyCalendarVO> getStartDayEventsOnlyY(String userId);
    List<MyCalendarVO> getEndDayEventsOnlyY(String userId);
    List<MyCalendarVO> getStartDayEventsOnlyN(String userId);
    List<MyCalendarVO> getEndDayEventsOnlyN(String userId);

    //찜 추가 삭제
    public void insertFavorite(@Param("empSeqNo") int empSeqNo,
                               @Param("userId") String userId);

    public void deleteFavorite(@Param("empSeqNo") int empSeqNo,
                               @Param("userId") String userId);

    List<Integer> findFavoriteEmpSeqNosUserId(@Param("userId") String userId);
<<<<<<< HEAD
    List<MyCalendarVO> findEventsByEmpSeqNos(@Param("empSeqNos") List<Integer> empSeqNos);
=======
<<<<<<< HEAD
    List<MyCalendarVO> findEventsByEmpSeqNos(@Param("empSeqNos") List<Integer> empSeqNos);
=======
>>>>>>> 8579913d0262c23a7f7ec1022296738779c2527e
>>>>>>> 12b5293c154ec5abb9300fd07fa4ae07deacd78c

}
