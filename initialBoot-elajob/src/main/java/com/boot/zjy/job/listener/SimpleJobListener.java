package com.boot.zjy.job.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jiayizhang on 2018/11/12.
 * 每台作业节点均执行的监听
 若作业处理作业服务器的文件，处理完成后删除文件，可考虑使用每个节点均执行清理任务。
 此类型任务实现简单，且无需考虑全局分布式任务是否完成，请尽量使用此类型监听器
 */
@Component
public class SimpleJobListener implements ElasticJobListener {
    private Logger logger = LoggerFactory.getLogger(SimpleJobListener.class);
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {

        ObjectMapper mapper = new ObjectMapper();
        String beforeJobExecutedJobInFo=null;
        try {
            beforeJobExecutedJobInFo=mapper.writeValueAsString(shardingContexts);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info("SimpleJobListener 启动前监听:"+shardingContexts.getJobName()+shardingContexts.getTaskId()+",详细:"+beforeJobExecutedJobInFo);

    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        logger.info("SimpleJobListener 执行完成:",shardingContexts.getJobName(),shardingContexts.getTaskId());
    }
}
