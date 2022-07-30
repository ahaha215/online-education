package com.lt.staservice.schedule;

import com.lt.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时统计任务
 *
 * @Author lt
 */
@Component
public class StaSchedule {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void staTask(){
        System.out.println("定时进行数据统计");
    }
}
