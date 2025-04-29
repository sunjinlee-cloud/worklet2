package com.project2.worklet.calendarService;

import com.project2.worklet.component.CalendarVO;
import com.project2.worklet.handlerInterceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        List<CalendarVO> events = calendarMapper.getAllEvent();
        log.info("Fetched {} events", events.size());

        if(userId == null) {
            log.warn("UserId is null, setting all favorites to false");
            for(CalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        log.info("UserId from session: {}", userId);

        List<Integer> favoriteEmpseqNos = calendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행
        log.info("Favorite empSeqNos for user {}: {}", userId, favoriteEmpseqNos);

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(CalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);
        }


        return events;
    }

    @Override
    public List<CalendarVO> getStartDayEvents(String userId) {
        //사용자의 즐겨찾기 목록을 가져오기

        List<CalendarVO> events = calendarMapper.getAllEvent();
        log.info("Fetched {} events", events.size());

        if(userId == null) {
            log.warn("UserId is null, setting all favorites to false");
            for(CalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        log.info("UserId from session: {}", userId);

        List<Integer> favoriteEmpseqNos = calendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행
        log.info("Favorite empSeqNos for user {}: {}", userId, favoriteEmpseqNos);

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(CalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);
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
