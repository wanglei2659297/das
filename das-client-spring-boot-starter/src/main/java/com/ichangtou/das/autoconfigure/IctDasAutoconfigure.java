package com.ichangtou.das.autoconfigure;

import com.ichangtou.das.locator.IctClientConfigureLoader;
import com.ichangtou.das.locator.IctConnectionLocator;
import com.ichangtou.das.locator.IctDataSourceLocator;
import com.ichangtou.das.properties.IctDasProperties;
import com.ichangtou.das.provider.IctDataSourceConfigureProvider;
import com.ppdai.das.core.ConnectionLocator;
import com.ppdai.das.core.DasLogger;
import com.ppdai.das.core.DatabaseSelector;
import com.ppdai.das.core.DefaultDatabaseSelector;
import com.ppdai.das.core.DefaultLogger;
import com.ppdai.das.core.helper.ServiceLoaderHelper;
import com.ppdai.das.core.task.DefaultTaskFactory;
import com.ppdai.das.core.task.TaskFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/01 12:04
 */
@Configuration
@AutoConfigureAfter(ServiceLoaderHelper.class)
@Slf4j
public class IctDasAutoconfigure implements ApplicationContextAware {

    @Autowired
    IctDasProperties ictDasProperties;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    ServiceLoaderHelper serviceLoaderHelper(){
        return new ServiceLoaderHelper(applicationContext);
    }

    @Bean
    DatabaseSelector databaseSelector() throws Exception {
        log.info("初始化 DatabaseSelector");
        return new DefaultDatabaseSelector();
    }

    /**
     * 动态数据源
     */
    @Bean
    IctDataSourceLocator ictDataSourceLocator() {
        serviceLoaderHelper();
        return new IctDataSourceLocator(ictDasProperties);
    }

    @Bean
    IctDataSourceConfigureProvider ictDataSourceConfigureProvider() {
        log.info("初始化 IctDataSourceConfigureProvider");
        return new IctDataSourceConfigureProvider(ictDasProperties);
    }

    /**
     * 连接定位器
     */
    @Bean
    ConnectionLocator connectionLocator() throws Exception {
        log.info("初始化 ConnectionLocator");
        return new IctConnectionLocator(ictDataSourceConfigureProvider(), ictDataSourceLocator());
    }

    @Bean
    TaskFactory taskFactory() {
        log.info("初始化 TaskFactory");
        return new DefaultTaskFactory();
    }

    @Bean
    DasLogger dasLogger() {
        log.info("初始化 DasLogger");
        return new DefaultLogger();
    }

    @Bean
    IctClientConfigureLoader ictClientConfigureLoader() throws Exception {
        log.info("初始化 IctClientConfigureLoader");
        return new IctClientConfigureLoader(connectionLocator(), databaseSelector(), taskFactory(), dasLogger(), ictDasProperties);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("初始化 applicationContext");
        this.applicationContext = applicationContext;
    }
}