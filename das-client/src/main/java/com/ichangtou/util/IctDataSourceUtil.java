package com.ichangtou.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.ichangtou.IctPoolProperties;
import com.ichangtou.hikari.IctHikariPoolProperties;
import com.ichangtou.hikari.IctHikariPoolPropertiesHelper;
import com.ichangtou.tomcat.IctTomcatDataSource;
import com.ichangtou.tomcat.IctTomcatPoolProperties;
import com.ichangtou.tomcat.IctTomcatPoolPropertiesHelper;
import com.ichangtou.tomcat.IctTomcatPoolPropertiesHolder;
import com.ppdai.das.core.configure.DataSourceConfigureConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 10:24
 */
public class IctDataSourceUtil {

    public static String getDataSourceName(DataSource dataSource) {
        if (dataSource instanceof HikariDataSource) {
            return ((HikariDataSource) dataSource).getPoolName();
        } else if (dataSource instanceof org.apache.tomcat.jdbc.pool.DataSource) {
            return ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).getPoolName();
        }

        throw new RuntimeException("get pool name exception");
    }

    public static DataSource createDataSource(Object propObj) throws SQLException {
        //tomcat数据源
        if (DataSourceConfigureConstants.DATASOURCE_CLASS_TOMCAT.equals(IctDataSourceTypeUtil.getDataSourceType())) {
            PoolProperties poolProperties = (PoolProperties) propObj;
            //转化为tomcat的连接池配置对象
            PoolProperties p = IctTomcatPoolPropertiesHelper.getInstance().copy(poolProperties);
            IctTomcatPoolPropertiesHolder.getInstance().setPoolProperties(p);
            return new IctTomcatDataSource(p);

        } else if (DataSourceConfigureConstants.DATASOURCE_CLASS_HIKARI.equals(IctDataSourceTypeUtil.getDataSourceType())) {
            HikariConfig from = (HikariConfig) propObj;
            HikariConfig hikariConfig = IctHikariPoolPropertiesHelper.getInstance().copy(from);
            return new HikariDataSource(hikariConfig);

        } else if (DataSourceConfigureConstants.DATASOURCE_CLASS_DRUID.equals(IctDataSourceTypeUtil.getDataSourceType())) {
            DruidDataSource druidDataSource = (DruidDataSource) propObj;
            druidDataSource.init();
            return druidDataSource;
        }
        throw new RuntimeException("unsupported datasource:" + propObj);
    }

}