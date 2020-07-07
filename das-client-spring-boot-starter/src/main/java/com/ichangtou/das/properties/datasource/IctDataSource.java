package com.ichangtou.das.properties.datasource;

import com.ichangtou.druid.IctDruidiPoolProperties;
import com.ichangtou.hikari.IctHikariPoolProperties;
import com.ichangtou.tomcat.IctTomcatPoolProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 连接池配置
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/03 11:47
 */
@Getter
@Setter
public class IctDataSource {

    /**
     * hikari连接池配置
     */
    private IctHikariPoolProperties hikari;

    /**
     * tomcat连接池配置
     */
    private IctTomcatPoolProperties tomcat;

    /**
     * druid连接池配置
     */
    private IctDruidiPoolProperties druid;

}