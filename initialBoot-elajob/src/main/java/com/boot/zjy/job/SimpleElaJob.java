package com.boot.zjy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by jiayizhang on 2018/11/9.
 */


public class SimpleElaJob implements SimpleJob
{
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(new Date()+" job名称 = "+shardingContext.getJobName()
                +",分片数量"+shardingContext.getShardingTotalCount()
                +",当前分区:"+shardingContext.getShardingItem()
                +",当前分区名称:"+shardingContext.getShardingParameter()
                +",当前自定义参数:"+shardingContext.getJobParameter()+";============start=================");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
