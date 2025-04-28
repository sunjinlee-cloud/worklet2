package com.project2.worklet.calendarService;

import com.project2.worklet.component.CalendarVO;
import com.project2.worklet.handlerInterceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("calendar")
@Slf4j
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private CalendarMapper calendarMapper;
    @Autowired
    private LoginInterceptor loginInterceptor;


    @Override
    public List<CalendarVO> getAllEvent(String userId) {

        //사용자의 즐겨찾기 목록을 가져오기
        List<Integer> favoriteEmpseqNos = calendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행

        List<CalendarVO> events = calendarMapper.getAllEvent();

        //디버깅
        log.info("Fetched events: {}", events);  // 여기에 로그를 추가

        if (events == null || events.isEmpty()) {
            log.warn("No events found in the database");
        }



        for(CalendarVO event : events) { //반복문, 이벤트가 사용자의 찜 목록에 있는지 확인하고 하트 상태 설정
            if(favoriteEmpseqNos.contains(event.getEmpSeqNo())) { // 사용자가 찜한 이벤트의 empSeqNo와 현재 이벤트의 empSeoNo가 일치하는 지 확인
                event.setFavorite(true); // 비교해서 true = 찜해던 거라는 뜻, setFavorite을 true로 바꿔서 빨간 하트로
            } else {
                event.setFavorite(false);
            }
        }


        return events;
    }


    //찜기능
    @Override
    public void addFavorite(int empSeqNo, String userId) {
        log.info("Adding favorite: EmpSeqNo = {}, UserId = {}", empSeqNo, userId);
        calendarMapper.insertFavorite(empSeqNo, userId);
    }

    @Override
    public void removeFavorite(int empSeqNo, String userId) {
        log.info("Removing favorite: EmpSeqNo = {}, UserId = {}", empSeqNo, userId);
        calendarMapper.deleteFavorite(empSeqNo, userId);

    }



    //즐겨찾기 목록가져오기
    @Override
    public List<Integer> getFavoriteEmpSeqNos(String userId) {
        return calendarMapper.findFavoriteEmpSeqNosUserId(userId);
    };



}
