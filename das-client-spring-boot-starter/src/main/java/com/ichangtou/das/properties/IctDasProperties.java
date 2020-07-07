package com.ichangtou.das.properties;

import com.ichangtou.das.properties.das.IctDataBase;
import com.ichangtou.das.properties.das.IctDataBaseSet;
import com.ichangtou.das.properties.datasource.IctDataSource;
import com.ichangtou.das.properties.datasource.IctGlobalSetting;
import com.ichangtou.druid.IctDruidiPoolProperties;
import com.ichangtou.hikari.IctHikariPoolProperties;
import com.ichangtou.tomcat.IctTomcatPoolProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/03 10:45
 */
@Configuration
@ConfigurationProperties(prefix = "ict.das")
@Setter
@Getter
public class IctDasProperties {

    private String appId;

    /**
     * das名称
     */
    private String name;

    /**
     * 逻辑库集合
     */
    private List<IctDataBaseSet> dataBaseSet;

    /**
     * hikari物理库配置(支持hikari,tomcat,druid 3中连接池)
     */
    private List<IctHikariPoolProperties> hikari;
    /**
     * tomcat物理库配置(支持hikari,tomcat,druid 3中连接池)
     */
    private List<IctTomcatPoolProperties> tomcat;
    /**
     * druid物理库配置(支持hikari,tomcat,druid 3中连接池)
     */
    private List<IctDruidiPoolProperties> druid;

    /**
     * 全局配置
     */
    private IctGlobalSetting global;

    private Map<String, String> settings = new HashMap<>();

    //以bean的方式声明，不在这里配置了
//    private String logListener;
//    private String taskFactory;
//    private String connectionLocator;
//    private String databaseSelector;

}