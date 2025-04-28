package com.project2.worklet.calendarService;

import com.project2.worklet.component.CalendarVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CalendarMapper {
    List<CalendarVO> getAllEvent();

    //찜 추가 삭제
    public void insertFavorite(@Param("empSeqNo") int empSeqNo,
                        @Param("userId") String userId);

    public void deleteFavorite(@Param("empSeqNo") int empSeqNo,
                        @Param("userId") String userId);

    List<Integer> findFavoriteEmpSeqNosUserId(@Param("userId") String userId);

}



