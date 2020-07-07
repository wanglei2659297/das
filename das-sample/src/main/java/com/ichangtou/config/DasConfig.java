package com.ichangtou.config;

import com.ichangtou.das.autoconfigure.IctDasAutoconfigure;
import com.ichangtou.das.locator.IctClientConfigureLoader;
import com.ppdai.das.client.DasClient;
import com.ppdai.das.client.DasClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/06/30 10:45
 */
@Configuration
@AutoConfigureAfter(IctDasAutoconfigure.class)
@Slf4j
public class DasConfig {
    @Bean
    public DasClient dasClient(IctClientConfigureLoader ictClientConfigureLoader) throws SQLException {
        log.info("初始化 DasClient");
        DasClientFactory.setClientConfigureLoader(ictClientConfigureLoader);
        return DasClientFactory.getClient("das_shardtest01");
    }
}