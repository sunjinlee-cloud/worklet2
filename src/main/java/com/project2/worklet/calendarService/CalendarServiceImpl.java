package com.project2.worklet.calendarService;

import com.project2.worklet.component.CalendarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("calendar")
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private CalendarMapper calendarMapper;

    @Override
    public List<CalendarVO> getAllEvent() {
        return calendarMapper.getAllEvent();
    }
}
