package com.boot.zjy;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by jiayizhang on 2018/10/27.
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class
})
@ImportResource(locations= {"classpath*:spring.xml"})
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class, args);
    }


    @Bean(initMethod = "init",name = "regCenter")
    public ZookeeperRegistryCenter regCenter(@Value("${elaticjob.zookeeper.server-lists}") final String serverList, @Value("${elaticjob.zookeeper.namespaces}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }
}
