package com.ichangtou.hikari;

import com.ichangtou.IctDataSource;
import com.ichangtou.tomcat.IctTomcatDataSource;
import com.ichangtou.tomcat.IctTomcatPoolPropertiesHelper;
import com.ichangtou.tomcat.IctTomcatPoolPropertiesHolder;
import com.ppdai.das.core.configure.DataSourceConfigure;
import com.ppdai.das.core.helper.ServiceLoaderHelper;
import com.ppdai.das.core.log.Callback;
import com.ppdai.das.core.log.DefaultLoggerImpl;
import com.ppdai.das.core.log.ILogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/01 17:40
 */
public class IctHikariDataSource extends HikariDataSource implements IctDataSource {

    private Logger LOGGER = LoggerFactory.getLogger(IctHikariDataSource.class);

    private IctHikariPoolPropertiesHelper poolPropertiesHelper = IctHikariPoolPropertiesHelper.getInstance();
    private static ILogger ilogger = ServiceLoaderHelper.getInstance(ILogger.class, DefaultLoggerImpl.class);
    private static final String DAL = "DAL";

    public IctHikariDataSource() {
    }

    public IctHikariDataSource(HikariConfig p) {
        super(p);
    }

    @Override
    public DataSource createDatasource(String name, DataSourceConfigure dataSourceConfigure) {
        HikariConfig p = poolPropertiesHelper.convert(dataSourceConfigure);
        IctHikariPoolPropertiesHolder.getInstance().setPoolProperties(p);


        DataSource dataSource = new IctHikariDataSource(p);

        String message = String.format("Datasource[name=%s, Driver=%s] created,connection url:%s", name,
                p.getDriverClassName(), dataSourceConfigure.getConnectionUrl());
        LOGGER.info(message);

        return dataSource;
    }

    @Override
    public int getActive() {
        return 1;
    }

    @Override
    public Object getPool() {
        return super.getDataSource();
    }

    @Override
    public int getIdle() {
        return 0;
    }

    @Override
    public void purge() {
    }

    @Override
    public void close(boolean flag) {
        super.close();
    }

    @Override
    public void close() {
        super.close();
    }
}