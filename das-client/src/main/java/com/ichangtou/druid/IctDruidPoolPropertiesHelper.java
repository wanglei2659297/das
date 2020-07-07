package com.ichangtou.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.ichangtou.IctPoolProperties;
import com.ichangtou.IctPoolPropertiesHelper;
import com.ppdai.das.core.configure.DataSourceConfigureConstants;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/01 18:04
 */
public class IctDruidPoolPropertiesHelper implements DataSourceConfigureConstants, IctPoolPropertiesHelper {

    private volatile static IctDruidPoolPropertiesHelper helper = null;

    public synchronized static IctDruidPoolPropertiesHelper getInstance() {
        if (helper == null) {
            helper = new IctDruidPoolPropertiesHelper();
        }
        return helper;
    }

    public Object convertIctProperties(IctPoolProperties ictPoolPropertie) throws SQLException {
        IctDruidiPoolProperties ictDruidiPoolProperties = (IctDruidiPoolProperties) ictPoolPropertie;
        DruidDataSource druid = new DruidDataSource();
        druid.setName(ictDruidiPoolProperties.getName());
        druid.setUrl(ictDruidiPoolProperties.getUrl());
        druid.setUsername(ictDruidiPoolProperties.getUsername());
        druid.setPassword(ictDruidiPoolProperties.getPassword());
        druid.setInitialSize(ictDruidiPoolProperties.getInitialSize());
        druid.setMinIdle(ictDruidiPoolProperties.getMinIdle());
        druid.setMaxActive(ictDruidiPoolProperties.getMaxActive());
        druid.setMaxWait(ictDruidiPoolProperties.getMaxWait());
        druid.setTimeBetweenEvictionRunsMillis(ictDruidiPoolProperties.getTimeBetweenEvictionRunsMillis());
        druid.setMinEvictableIdleTimeMillis(ictDruidiPoolProperties.getMinEvictableIdleTimeMillis());
        druid.setValidationQuery(ictDruidiPoolProperties.getValidationQuery());
        druid.setTestWhileIdle(ictDruidiPoolProperties.isTestWhileIdle());
        druid.setTestOnBorrow(ictDruidiPoolProperties.isTestOnBorrow());
        druid.setTestOnReturn(ictDruidiPoolProperties.isTestOnReturn());
        druid.setPoolPreparedStatements(ictDruidiPoolProperties.isPoolPreparedStatements());
        druid.setMaxPoolPreparedStatementPerConnectionSize(ictDruidiPoolProperties.getMaxPoolPreparedStatementPerConnectionSize());
        druid.setFilters(ictDruidiPoolProperties.getFilters());
        druid.setDefaultReadOnly(ictDruidiPoolProperties.isReadOnly());
        druid.setConnectProperties(strToProp(ictDruidiPoolProperties.getConnectProperties()));

        return druid;
    }

    private Properties strToProp(String str) {
        Properties properties = new Properties();

        if (StringUtils.isNotBlank(str) && str.contains(";")) {
            String[] split = str.split(";");
            for (String s : split) {
                if (StringUtils.isNotBlank(s) && s.contains("=")) {
                    String[] arr = s.split("=");
                    if (StringUtils.isNotBlank(arr[0]) && StringUtils.isNotBlank(arr[1])) {
                        properties.put(arr[0], arr[1]);
                    }
                }
            }
        }
        return properties;
    }
}