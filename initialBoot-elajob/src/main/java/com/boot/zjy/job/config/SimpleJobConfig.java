package com.boot.zjy.job.config;

import com.boot.zjy.job.SimpleElaJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by jiayizhang on 2018/11/14.
 */
@Configuration
public class SimpleJobConfig {
    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    private JobEventConfiguration jobEventConfiguration;



    @Resource
    private ElasticJobListener simpleJobListener;

    @Bean
    public SimpleJob simpleJob() {
        return new SimpleElaJob();
    }

    /**
     * elaticjob.simpleJob.cron=0/20 * * * * ?
     elaticjob.simpleJob.sharding-total-count=2
     elaticjob.sharding-item-parameters="0=a"
     elaticjob.simpleJob.sharding-item-parameters=0=a,1=b
     * @return
     */
    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final SimpleJob simpleJob, @Value("${elaticjob.simpleJob.cron}") final String cron, @Value("${elaticjob.simpleJob.sharding-total-count}") final int shardingTotalCount,
                                           @Value("${elaticjob.sharding-item-parameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration,simpleJobListener);
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {

        JobCoreConfiguration jobCoreConfiguration=  JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).failover(true).build();

        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(jobCoreConfiguration, jobClass.getCanonicalName())).overwrite(true).build();
    }
}
