package com.boot.zjy;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.sql.SQLException;

/**
 * Created by jiayizhang on 2018/10/27.
 */
@Configuration
@MapperScan(basePackages = MatserDruidConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MatserDruidConfig {
    private Logger logger = LoggerFactory.getLogger(MatserDruidConfig.class);

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.boot.zjy.master.mapper";
    static final String MAPPER_LOCATION = "classpath*:mapper/master/*.xml";
    @Value("${spring.master.datasource.url}")
    private String dbUrl;
    @Value("${spring.master.datasource.username}")
    private String username;
    @Value("${spring.master.datasource.password}")
    private String password;
    @Value("${spring.master.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.master.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.master.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.master.datasource.maxActive}")
    private int maxActive;
    @Value("${spring.master.datasource.maxWait}")
    private int maxWait;
    @Value("${spring.master.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.master.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.master.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.master.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.master.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.master.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.master.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${spring.master.datasource.filters}")
    private String filters;

    public MatserDruidConfig() {
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        this.logger.info("init Druid Servlet Configuration ");
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings(new String[]{"/druid/*"});
        reg.addInitParameter("loginUsername", "druid");
        reg.addInitParameter("loginPassword", "123456");
        reg.addInitParameter("allow", "");
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addUrlPatterns(new String[]{"/*"});
        return filterRegistrationBean;
    }

    @Bean(name = "masterDruidDataSource")
    @Primary
    public DruidDataSource masterDruidDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(this.username);
        datasource.setPassword(this.password);
        datasource.setDriverClassName(this.driverClassName);
        datasource.setInitialSize(this.initialSize);
        datasource.setMinIdle(this.minIdle);
        datasource.setMaxActive(this.maxActive);
        datasource.setMaxWait((long) this.maxWait);
        datasource.setTimeBetweenEvictionRunsMillis((long) this.timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis((long) this.minEvictableIdleTimeMillis);
        datasource.setValidationQuery(this.validationQuery);
        datasource.setTestWhileIdle(this.testWhileIdle);
        datasource.setTestOnBorrow(this.testOnBorrow);
        datasource.setTestOnReturn(this.testOnReturn);
        datasource.setPoolPreparedStatements(this.poolPreparedStatements);

        try {
            datasource.setFilters(this.filters);
        } catch (SQLException var3) {
            this.logger.error("druid configuration initialization filter", var3);
        }

        return datasource;
    }
      @Bean(name="materSqlSessionTemplate")
      @Primary
      public SqlSessionTemplate materSqlSessionTemplate(SqlSessionFactory masterSqlSessionFactory) {
            return new SqlSessionTemplate(masterSqlSessionFactory);
       }

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier(value = "masterDruidDataSource") DruidDataSource masterDruidDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDruidDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MatserDruidConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
