package com.lt.staservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.commonutils.R;
import com.lt.staservice.client.UcenterClient;
import com.lt.staservice.entity.StatisticsDaily;
import com.lt.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-07-25
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController{

    /**
     * 统计服务层
     */
    @Autowired
    StatisticsDailyService statisticsDailyService;

    /**
     * 用户中心远程调用
     */
    @Autowired
    UcenterClient ucenterClient;


    /**
     * 统计
     * @param day
     * @return
     */
    @GetMapping("calculationSta/{day}")
    public R calculationSta(@PathVariable("day") String day){

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",day);
        StatisticsDaily oldSta = statisticsDailyService.getOne(queryWrapper);


        R resultR = ucenterClient.countRegisterSum(day);
        int registerSum = (int) resultR.getData().get("registerSum");

        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setRegisterNum(registerSum);
        statisticsDaily.setLoginNum(666);
        statisticsDaily.setVideoViewNum(666);
        statisticsDaily.setCourseNum(666);

        if (oldSta == null){
            statisticsDailyService.save(statisticsDaily);
        } else {
            statisticsDaily.setId(oldSta.getId());
            statisticsDailyService.updateById(statisticsDaily);
        }

        return R.ok();
    }

    /**
     * 获取统计数据
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("getChart/{type}/{begin}/{end}")
    public R getChart(@PathVariable("type") String type,
                      @PathVariable("begin") String begin,
                      @PathVariable("end") String end){

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated",begin,end);

        List<StatisticsDaily> list = statisticsDailyService.list(queryWrapper);


        // 遍历数据集，使用map进行按需封装
        List<String> dateList = new ArrayList<>();
        List<Integer> dataList = new ArrayList<>();


        for (StatisticsDaily statisticsDaily : list) {

            // 封装日期
            dateList.add(statisticsDaily.getDateCalculated());

            // 封装单日数值
            switch (type){
                case "login_num":
                    dataList.add(statisticsDaily.getLoginNum());
                    break;

                case "register_num":
                    dataList.add(statisticsDaily.getRegisterNum());
                    break;

                case "video_view_num":
                    dataList.add(statisticsDaily.getVideoViewNum());
                    break;

                case "course_num":
                    dataList.add(statisticsDaily.getCourseNum());
                    break;
            }

        }

        return R.ok().data("dateList",dateList).data("dataList",dataList);
    }
}

