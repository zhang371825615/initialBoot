package com.boot.zjy.job.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by jiayizhang on 2018/11/14.
 */
@Configuration
public class JobEventConfig {
    @Autowired
    private DruidDataSource masterDruidDataSource;

    @Bean
    public JobEventConfiguration jobEventConfiguration() {
        return new JobEventRdbConfiguration(masterDruidDataSource);
    }
}
