package com.mp.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;

/**
 * @author ycn
 */
@Configuration
public class StartTimer {

    private static final Logger LOG = LoggerFactory.getLogger(StartTimer.class);

    @Value(value = "${user.time}")
    private  String userTime;

    @Value(value = "${user.ServePath}")
    private  String serveUrl;

    /**
     * 时间
      */
    static Timer timer = new Timer();

    @Bean
    public void startTim() {
        LOG.info("循环连接设备的间隔时间字符串：[{}],数字:{}",userTime,Integer.parseInt(userTime.trim()));
        //Integer.parseInt(userTime)
        timer.schedule(new Mytask(), 1000, Integer.parseInt(userTime.trim()));
    }
}
