package com.project2.worklet.calendarService;

import com.project2.worklet.component.CalendarVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {
    List<CalendarVO> getAllEvent();
}
