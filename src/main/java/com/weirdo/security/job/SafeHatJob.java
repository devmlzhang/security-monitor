package com.weirdo.security.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author zhanghui
 * @Description 每天定时删除安全帽前一天的数据
 * @Date  2018/11/28
 **/

@Component
@EnableScheduling
public class SafeHatJob {

    private static final Logger logger = LoggerFactory.getLogger(SafeHatJob.class);



    /*
     * @Author zhanghui
     * @Description 1  秒（0~59）
      2  分钟（0~59）
      3 小时（0~23）
      4  天（0~31）
      5 月（0~11）
      6  星期（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
      7.年份（1970－2099）
     * @Date  2018/12/25
     **/
    @Scheduled(cron = "0 0 1 * * ?")
    public void executJob() {

    }
}
