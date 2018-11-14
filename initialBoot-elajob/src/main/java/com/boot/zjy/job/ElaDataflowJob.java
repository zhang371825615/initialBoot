package com.boot.zjy.job;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.druid.support.json.JSONParser;
import com.boot.zjy.master.mapper.UserEntityMapper;
import com.boot.zjy.user.entity.UserEntity;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by jiayizhang on 2018/11/10.
 */
public class ElaDataflowJob implements DataflowJob<UserEntity> {

    @Autowired
    private UserEntityMapper userEntityMapper;
    @Override
    public List<UserEntity> fetchData(ShardingContext shardingContext) {
        UserEntity  u=new UserEntity(shardingContext.getShardingItem(),shardingContext.getShardingTotalCount());
        return userEntityMapper.selectListWithzone(u);

    }

    @Override
    public void processData(ShardingContext shardingContext, List<UserEntity> data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
           String json= mapper.writeValueAsString(data);
            System.out.println("data:"+json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("data长度:"+data.size());
    }
}
