package com.project2.worklet.component;

import com.project2.worklet.jobPostingService.JobPostingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MyScheduler {

    private JobPostingService jobPostingService;

    public MyScheduler(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

     //매일 오전 7시에 실행
    @Scheduled(cron = "30 03 12 * * *")
    public void runEveryMorningAt7() {
        System.out.println(jobPostingService.postList());
    }

    //매일 오후 7시에 실행 - 공채공고량이 생각보다 많아서 오전/오후에 한번씩 db 입력
//    @Scheduled(cron = "00 00 19 * * *")
//    public void runEveryEveningAt7() {
//        System.out.println(jobPostingService.postList());
//    }

    //디테일 가져오는거 되는지 테스트
    @Scheduled(cron = "35 41 17 * * *")
    public void getDetailTest() {
        List<String> list = new ArrayList<>();
//        list.add("122397");
        list.add("122396");
        list.add("122395");
        list.add("122394");
        list.add("122393");
        list.add("122392");
        list.add("122391");
        list.add("122390");
        System.out.println(jobPostingService.postDetail(list));
    }



    // 매일 오전 7시에 실행
//    @Scheduled(cron = "0 23 15 * * *")
//    public void runEveryMorningAt7() {
//        System.out.println("🌅 아침 7시 실행됨! " + java.time.LocalDateTime.now());
//
//    }
}
