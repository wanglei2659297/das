package com.ichangtou.hikari;

import com.ichangtou.IctPoolProperties;
import com.ichangtou.IctPoolPropertiesHelper;
import com.ppdai.das.core.configure.DataSourceConfigure;
import com.zaxxer.hikari.HikariConfig;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.Properties;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 14:05
 */
public class IctHikariPoolPropertiesHelper implements IctPoolPropertiesHelper {
    private volatile static IctHikariPoolPropertiesHelper helper = null;

    public synchronized static IctHikariPoolPropertiesHelper getInstance() {
        if (helper == null) {
            helper = new IctHikariPoolPropertiesHelper();
        }
        return helper;
    }

    public HikariConfig copy(HikariConfig from) {
        HikariConfig properties = new HikariConfig();

        properties.setJdbcUrl(from.getJdbcUrl());
        properties.setUsername(from.getUsername());
        properties.setPassword(from.getPassword());
        properties.setDriverClassName(from.getDriverClassName());
        properties.setAllowPoolSuspension(from.isAllowPoolSuspension());
        properties.setReadOnly(from.isReadOnly());
        properties.setAutoCommit(from.isAutoCommit());
        properties.setCatalog(from.getCatalog());
        properties.setConnectionInitSql(from.getConnectionInitSql());
        properties.setConnectionTestQuery(from.getConnectionTestQuery());
        properties.setConnectionTimeout(from.getConnectionTimeout());
        properties.setDataSourceClassName(from.getDataSourceClassName());
        properties.setDataSourceJNDI(from.getDataSourceJNDI());
        properties.setDataSourceProperties(from.getDataSourceProperties());
        properties.setIdleTimeout(from.getIdleTimeout());
        properties.setInitializationFailTimeout(from.getInitializationFailTimeout());
        properties.setIsolateInternalQueries(from.isIsolateInternalQueries());
        properties.setLeakDetectionThreshold(from.getLeakDetectionThreshold());
        properties.setMaxLifetime(from.getMaxLifetime());
        properties.setMaximumPoolSize(from.getMaximumPoolSize());
        properties.setMinimumIdle(from.getMinimumIdle());
        properties.setPoolName(from.getPoolName());
        properties.setReadOnly(from.isReadOnly());
        properties.setRegisterMbeans(from.isRegisterMbeans());
        properties.setTransactionIsolation(from.getTransactionIsolation());
        properties.setValidationTimeout(from.getValidationTimeout());

        return properties;
    }

    private Properties dealConnectionProperties(String connectionPropertiesStr) {
        Properties properties = new Properties();
        if (StringUtils.isNotBlank(connectionPropertiesStr) && connectionPropertiesStr.contains(";")) {
            String[] propertiesArray = connectionPropertiesStr.split(";");
            if (ArrayUtils.isNotEmpty(propertiesArray)) {
                for (String prop : propertiesArray) {
                    if (prop.contains("=")) {
                        String[] split = prop.split("=");
                        if (StringUtils.isNotBlank(split[0]) && StringUtils.isNotBlank(split[1])) {
                            properties.put(split[0], split[1]);
                        }
                    }
                }
            }
        }
        return properties;
    }

    //TODO 重写
    public HikariConfig convert(DataSourceConfigure config) {
        HikariConfig properties = new HikariConfig();

        /**
         * It is assumed that user name/password/url/driver class name are provided in pool config If not, it should be
         * provided by the config provider
         */
        properties.setPoolName(config.getName());
        properties.setJdbcUrl(config.getConnectionUrl());
        properties.setUsername(config.getUserName());
        properties.setPassword(config.getPassword());
        properties.setDriverClassName(config.getDriverClass());

        //TODO 数据源属性读取
//        properties.setConnectionTestQuery(config.getProperty(VALIDATIONQUERY, DEFAULT_VALIDATIONQUERY));
//        properties.setValidationTimeout(
//                config.getIntProperty(VALIDATIONQUERYTIMEOUT, DEFAULT_VALIDATIONQUERYTIMEOUT * 1000));
//        properties.setIdleTimeout(
//                config.getIntProperty(MINEVICTABLEIDLETIMEMILLIS, DEFAULT_MINEVICTABLEIDLETIMEMILLIS));
//
//        properties.setMaxLifetime(config.getIntProperty(MAX_AGE, DEFAULT_MAXAGE));
//        properties.setMaximumPoolSize(config.getIntProperty(MAXACTIVE, DEFAULT_MAXACTIVE));
//        properties.setMinimumIdle(config.getIntProperty(MINIDLE, DEFAULT_MINIDLE));
//        properties.setConnectionTimeout(config.getIntProperty(MAXWAIT, DEFAULT_MAXWAIT));
//
//        properties.setDataSourceProperties(dealConnectionProperties(config.getProperty(CONNECTIONPROPERTIES, DEFAULT_CONNECTIONPROPERTIES)));
//
//        String initSQL = config.getProperty(INIT_SQL);
//        if (initSQL != null && !initSQL.isEmpty()) {
//            properties.setConnectionInitSql(initSQL);
//        }
//
//        String initSQL2 = config.getProperty(INIT_SQL2);
//        if (initSQL2 != null && !initSQL2.isEmpty()) {
//            properties.setConnectionInitSql(initSQL2);
//        }

        return properties;
    }

    public Object convertIctProperties(IctPoolProperties ictPoolPropertie) {

        IctHikariPoolProperties ictHikariPoolProperties = (IctHikariPoolProperties) ictPoolPropertie;

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName(ictHikariPoolProperties.getName());
        hikariConfig.setJdbcUrl(ictHikariPoolProperties.getUrl());
        hikariConfig.setUsername(ictHikariPoolProperties.getUsername());
        hikariConfig.setPassword(ictHikariPoolProperties.getPassword());
        hikariConfig.setDriverClassName(ictHikariPoolProperties.getDriverClassName());
        hikariConfig.setAllowPoolSuspension(ictHikariPoolProperties.isAllowPoolSuspension());
        hikariConfig.setAutoCommit(ictHikariPoolProperties.isAutoCommit());
        hikariConfig.setCatalog(ictHikariPoolProperties.getCatalog());
        hikariConfig.setConnectionInitSql(ictHikariPoolProperties.getConnectionInitSql());
        hikariConfig.setConnectionTestQuery(ictHikariPoolProperties.getConnectionTestQuery());
        hikariConfig.setConnectionTimeout(ictHikariPoolProperties.getConnectionTimeout());
        hikariConfig.setDataSourceClassName(ictHikariPoolProperties.getDataSourceClassName());
        hikariConfig.setDataSourceJNDI(ictHikariPoolProperties.getDataSourceJNDI());
        if (ictHikariPoolProperties.getDataSourceProperties() != null) {
            hikariConfig.setDataSourceProperties(dealConnectionProperties(ictHikariPoolProperties.getDataSourceProperties()));
        }
        hikariConfig.setIdleTimeout(ictHikariPoolProperties.getIdleTimeout());
        hikariConfig.setInitializationFailTimeout(ictHikariPoolProperties.getInitializationFailTimeout());
        hikariConfig.setIsolateInternalQueries(ictHikariPoolProperties.isIsolateInternalQueries());
        hikariConfig.setLeakDetectionThreshold(ictHikariPoolProperties.getLeakDetectionThreshold());
        hikariConfig.setMaxLifetime(ictHikariPoolProperties.getMaxLifetime());
        hikariConfig.setMaximumPoolSize(ictHikariPoolProperties.getMaximumPoolSize());
        hikariConfig.setMinimumIdle(ictHikariPoolProperties.getMinimumIdle());
        hikariConfig.setPoolName(ictHikariPoolProperties.getPoolName());
        hikariConfig.setReadOnly(ictHikariPoolProperties.isReadOnly());
        hikariConfig.setRegisterMbeans(ictHikariPoolProperties.isRegisterMbeans());
        hikariConfig.setTransactionIsolation(ictHikariPoolProperties.getTransactionIsolation());
        hikariConfig.setValidationTimeout(ictHikariPoolProperties.getValidationTimeout());

        return hikariConfig;
    }

    public String propertiesToString(Properties properties) {
        String result = "";
        try {
            if (properties != null && !properties.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    sb.append(entry.getKey().toString() + "=" + entry.getValue().toString() + ",");
                }
                result = sb.substring(0, sb.length() - 1);
            }
        } catch (Throwable e) {
        }
        return result;
    }

    public String mapToString(Map<String, String> map) {
        String result = "";
        try {
            if (map != null && map.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    sb.append(entry.getKey() + "=" + entry.getValue() + ",");
                }
                result = sb.substring(0, sb.length() - 1);
            }
        } catch (Throwable e) {
        }
        return result;
    }
}