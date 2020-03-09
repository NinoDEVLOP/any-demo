package com.fun.learn.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.fun.learn.config.properties.ServiceDataSourceProperties;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/1 11:27
 */
@Configuration
@EnableConfigurationProperties(ServiceDataSourceProperties.class)
public class IbeetlSqlConfig {

    @Primary
    @Bean(name = "sqlManagerFactoryBean")
    public SqlManagerFactoryBean sqlManagerFactoryBean(@Value("${spring.profiles.active}") String active,
                                                       @Qualifier("defaultDataSource") DataSource datasource) {
        SqlManagerFactoryBean factory = new SqlManagerFactoryBean();
        BeetlSqlDataSource source = new BeetlSqlDataSource();
        source.setMasterSource(datasource);
        factory.setCs(source);
        //数据库类型
        factory.setDbStyle(new MySqlStyle());

        if ("prd".equals(active)) {
            //debug模式 开发时使用
            factory.setInterceptors(new Interceptor[]{new DebugInterceptor()});
        }
        //命名转换方式
        factory.setNc(new UnderlinedNameConversion());
        factory.setSqlLoader(new ClasspathLoader("/sql"));
        return factory;
    }

    @Bean(name = "txManager")//开启事务管理
    public DataSourceTransactionManager txManager(
            @Qualifier("defaultDataSource") DataSource datasource) {
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(datasource);
        return dstm;
    }

    @Bean
    public DataSource defaultDataSource(ServiceDataSourceProperties properties) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setInitialSize(properties.getInitialSize());
        druidDataSource.setMaxActive(properties.getMaxActive());
        druidDataSource.setMaxWait(properties.getMaxWait());
        druidDataSource.setMinIdle(properties.getMinIdle());
        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(properties.getValidationQuery());
        druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(properties.isTestOnReturn());
        try {
            druidDataSource.setFilters(properties.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }
}
