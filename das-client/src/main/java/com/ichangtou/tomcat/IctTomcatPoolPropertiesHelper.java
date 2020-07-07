package com.ichangtou.tomcat;

import com.ichangtou.IctPoolProperties;
import com.ichangtou.IctPoolPropertiesHelper;
import com.ppdai.das.core.configure.DataSourceConfigure;
import com.ppdai.das.core.configure.DataSourceConfigureConstants;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.util.Map;
import java.util.Properties;

public class IctTomcatPoolPropertiesHelper implements DataSourceConfigureConstants, IctPoolPropertiesHelper {
    private volatile static IctTomcatPoolPropertiesHelper helper = null;

    public synchronized static IctTomcatPoolPropertiesHelper getInstance() {
        if (helper == null) {
            helper = new IctTomcatPoolPropertiesHelper();
        }
        return helper;
    }

    public PoolProperties copy(PoolProperties from) {
        PoolProperties properties = new PoolProperties();


        properties.setUrl(from.getUrl());
        properties.setUsername(from.getUsername());
        properties.setPassword(from.getPassword());
        properties.setDriverClassName(from.getDriverClassName());
        properties.setInitSQL(from.getInitSQL());
        properties.setInitialSize(from.getInitialSize());
        properties.setMaxActive(from.getMaxActive());
        properties.setMaxWait(from.getMaxWait());
        properties.setMaxIdle(from.getMaxIdle());
        properties.setMinIdle(from.getMinIdle());
        properties.setMaxAge(from.getMaxAge());
        properties.setConnectionProperties(from.getConnectionProperties());
        properties.setValidationQuery(from.getValidationQuery());
        properties.setValidationQueryTimeout(from.getValidationQueryTimeout());
        properties.setValidationInterval(from.getValidationInterval());


        properties.setTestOnReturn(from.isTestOnReturn());
        properties.setTestWhileIdle(from.isTestWhileIdle());
        properties.setTestOnBorrow(from.isTestOnBorrow());

        properties.setValidatorClassName(from.getValidatorClassName());

        properties.setTimeBetweenEvictionRunsMillis(from.getTimeBetweenEvictionRunsMillis());
        properties.setMinEvictableIdleTimeMillis(from.getMinEvictableIdleTimeMillis());

        properties.setJmxEnabled(from.isJmxEnabled());
        properties.setJdbcInterceptors(from.getJdbcInterceptors());

        properties.setRemoveAbandonedTimeout(from.getRemoveAbandonedTimeout());
        properties.setRemoveAbandoned(from.isRemoveAbandoned());
        properties.setLogAbandoned(from.isLogAbandoned());
        properties.setDefaultReadOnly(from.getDefaultReadOnly());

        return properties;
    }

    //TODO 重写
    public PoolProperties convert(DataSourceConfigure config) {
        PoolProperties properties = new PoolProperties();

        /**
         * It is assumed that user name/password/url/driver class name are provided in pool config If not, it should be
         * provided by the config provider
         */
        properties.setUrl(config.getConnectionUrl());
        properties.setUsername(config.getUserName());
        properties.setPassword(config.getPassword());
        properties.setDriverClassName(config.getDriverClass());

        properties.setTestWhileIdle(config.getBooleanProperty(TESTWHILEIDLE, DEFAULT_TESTWHILEIDLE));
        properties.setTestOnBorrow(config.getBooleanProperty(TESTONBORROW, DEFAULT_TESTONBORROW));
        properties.setTestOnReturn(config.getBooleanProperty(TESTONRETURN, DEFAULT_TESTONRETURN));

        properties.setValidationQuery(config.getProperty(VALIDATIONQUERY, DEFAULT_VALIDATIONQUERY));
        properties.setValidationQueryTimeout(
                config.getIntProperty(VALIDATIONQUERYTIMEOUT, DEFAULT_VALIDATIONQUERYTIMEOUT));
        properties.setValidationInterval(config.getLongProperty(VALIDATIONINTERVAL, DEFAULT_VALIDATIONINTERVAL));

        properties.setTimeBetweenEvictionRunsMillis(
                config.getIntProperty(TIMEBETWEENEVICTIONRUNSMILLIS, DEFAULT_TIMEBETWEENEVICTIONRUNSMILLIS));
        properties.setMinEvictableIdleTimeMillis(
                config.getIntProperty(MINEVICTABLEIDLETIMEMILLIS, DEFAULT_MINEVICTABLEIDLETIMEMILLIS));

        properties.setMaxAge(config.getIntProperty(MAX_AGE, DEFAULT_MAXAGE));
        properties.setMaxActive(config.getIntProperty(MAXACTIVE, DEFAULT_MAXACTIVE));
        properties.setMinIdle(config.getIntProperty(MINIDLE, DEFAULT_MINIDLE));
        properties.setMaxWait(config.getIntProperty(MAXWAIT, DEFAULT_MAXWAIT));
        properties.setInitialSize(config.getIntProperty(INITIALSIZE, DEFAULT_INITIALSIZE));

        properties.setRemoveAbandonedTimeout(
                config.getIntProperty(REMOVEABANDONEDTIMEOUT, DEFAULT_REMOVEABANDONEDTIMEOUT));
        properties.setRemoveAbandoned(config.getBooleanProperty(REMOVEABANDONED, DEFAULT_REMOVEABANDONED));
        properties.setLogAbandoned(config.getBooleanProperty(LOGABANDONED, DEFAULT_LOGABANDONED));

        properties.setConnectionProperties(config.getProperty(CONNECTIONPROPERTIES, DEFAULT_CONNECTIONPROPERTIES));

        String initSQL = config.getProperty(INIT_SQL);
        if (initSQL != null && !initSQL.isEmpty()) {
            properties.setInitSQL(initSQL);
        }

        String initSQL2 = config.getProperty(INIT_SQL2);
        if (initSQL2 != null && !initSQL2.isEmpty()) {
            properties.setInitSQL(initSQL2);
        }

        // This are current hard coded as default value
        properties.setJmxEnabled(DEFAULT_JMXENABLED);
        properties.setJdbcInterceptors(config.getProperty(JDBC_INTERCEPTORS, DEFAULT_JDBCINTERCEPTORS));

        return properties;
    }

    public Object convertIctProperties(IctPoolProperties ictPoolPropertie) {
        IctTomcatPoolProperties ictTomcatPoolProperties = (IctTomcatPoolProperties) ictPoolPropertie;
        PoolProperties properties = new PoolProperties();

        properties.setName(ictTomcatPoolProperties.getName());
        properties.setUrl(ictTomcatPoolProperties.getUrl());
        properties.setUsername(ictTomcatPoolProperties.getUsername());
        properties.setPassword(ictTomcatPoolProperties.getPassword());
        properties.setDriverClassName(ictTomcatPoolProperties.getDriverClassName());
        properties.setDefaultReadOnly(ictTomcatPoolProperties.isReadOnly());
        properties.setTestWhileIdle(ictTomcatPoolProperties.isTestWhileIdle());
        properties.setTestOnBorrow(ictTomcatPoolProperties.isTestOnBorrow());
        properties.setTestOnReturn(ictTomcatPoolProperties.isTestOnReturn());

        properties.setValidationQuery(ictTomcatPoolProperties.getValidationQuery());
        properties.setValidationQueryTimeout(ictTomcatPoolProperties.getValidationQueryTimeout());
        properties.setValidationInterval(ictTomcatPoolProperties.getValidationInterval());

        properties.setTimeBetweenEvictionRunsMillis(ictTomcatPoolProperties.getTimeBetweenEvictionRunsMillis());
        properties.setMinEvictableIdleTimeMillis(ictTomcatPoolProperties.getMinEvictableIdleTimeMillis());

        properties.setMaxAge(ictTomcatPoolProperties.getMaxAge());
        properties.setMaxActive(ictTomcatPoolProperties.getMaxActive());
        properties.setMinIdle(ictTomcatPoolProperties.getMinIdle());
        properties.setMaxWait(ictTomcatPoolProperties.getMaxWait());
        properties.setInitialSize(ictTomcatPoolProperties.getInitialSize());

        properties.setRemoveAbandonedTimeout(ictTomcatPoolProperties.getRemoveAbandonedTimeout());
        properties.setRemoveAbandoned(ictTomcatPoolProperties.isRemoveAbandoned());
        properties.setLogAbandoned(ictTomcatPoolProperties.isLogAbandoned());

        properties.setConnectionProperties(ictTomcatPoolProperties.getConnectionProperties());

        properties.setInitSQL(ictTomcatPoolProperties.getInitSQL());

        // This are current hard coded as default value
        properties.setJmxEnabled(ictTomcatPoolProperties.isJmxEnabled());
        properties.setJdbcInterceptors(ictTomcatPoolProperties.getJdbcInterceptors());



        return properties;
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
