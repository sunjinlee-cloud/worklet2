package com.project2.worklet.component;

import com.project2.worklet.jobPostingService.JobPostingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class MyScheduler {

    private JobPostingService jobPostingService;

    public MyScheduler(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

     //매일 오전 7시에 실행
    @Scheduled(cron = "10 49 18 * * *")
    public void runEveryMorningAt7() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        try {
            File file = new File("C:\\Users\\user\\Desktop\\updates.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(date);
            bw.write(jobPostingService.postList());
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Scheduled(cron = "00 00 10 * * *")
//    public void runEveryMondayMorningAt10() {
//        System.out.println(jobPostingService.postList());
//    }


    //디테일 가져오는거 되는지 테스트
//    @Scheduled(cron = "30 58 11 * * *")
//    public void getDetailTest() {
//        List<String> list = new ArrayList<>();
//        for(int i = 122660; i<=122680; i++) {
//            list.add(String.valueOf(i));
//        }
//
//        System.out.println(jobPostingService.postDetail(list));
//    }



    // 매일 오전 7시에 실행
//    @Scheduled(cron = "0 23 15 * * *")
//    public void runEveryMorningAt7() {
//        System.out.println("🌅 아침 7시 실행됨! " + java.time.LocalDateTime.now());
//
//    }
}
