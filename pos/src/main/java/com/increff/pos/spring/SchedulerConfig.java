package com.increff.pos.spring;

import com.increff.pos.dto.SalesDto;
import com.increff.pos.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@EnableAsync
public class SchedulerConfig {
    @Autowired
    SalesDto salesDto;

    @Async
    @Scheduled(cron = "00 00 12 * * *")
    public void createReport() {
        salesDto.createReport();; //todo: add on off
    }
}