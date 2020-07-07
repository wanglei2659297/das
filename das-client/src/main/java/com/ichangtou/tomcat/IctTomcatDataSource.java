package com.ichangtou.tomcat;

import com.ichangtou.IctDataSource;
import com.ppdai.das.core.configure.DataSourceConfigure;
import com.ppdai.das.core.helper.ServiceLoaderHelper;
import com.ppdai.das.core.log.Callback;
import com.ppdai.das.core.log.DefaultLoggerImpl;
import com.ppdai.das.core.log.ILogger;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/01 17:40
 */
public class IctTomcatDataSource extends org.apache.tomcat.jdbc.pool.DataSource implements IctDataSource, IctTomcatDataSourceMBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(IctTomcatDataSource.class);

    private IctTomcatPoolPropertiesHelper poolPropertiesHelper = IctTomcatPoolPropertiesHelper.getInstance();
    private static ILogger ilogger = ServiceLoaderHelper.getInstance(ILogger.class, DefaultLoggerImpl.class);
    private static final String DAL = "DAL";
    private static final String DATASOURCE_CREATE_DATASOURCE = "DataSource::createDataSource:%s";
    public static final String JMX_TOMCAT_DATASOURCE = "TomcatDataSource";

    public IctTomcatDataSource() {

    }

    public IctTomcatDataSource(PoolProperties p) {
        super(p);
    }

    public DataSource createDatasource(String name, DataSourceConfigure dataSourceConfigure) throws InstanceNotFoundException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        PoolProperties p = poolPropertiesHelper.convert(dataSourceConfigure);
        IctTomcatPoolPropertiesHolder.getInstance().setPoolProperties(p);
        final org.apache.tomcat.jdbc.pool.DataSource dataSource = new IctTomcatDataSource(p);

        String message = String.format("Datasource[name=%s, Driver=%s] created,connection url:%s", name,
                p.getDriverClassName(), dataSourceConfigure.getConnectionUrl());
        ilogger.logTransaction(DAL, String.format(DATASOURCE_CREATE_DATASOURCE, name), message, new Callback() {
            @Override
            public void execute() throws Exception {
                dataSource.createPool();
            }
        });
        LOGGER.info(message);

        registerDataSource(name, dataSource);

        return dataSource;
    }

    /**暂时不注册JMX*/
    private void registerDataSource(String name, DataSource dataSource) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InstanceNotFoundException {
//        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
//        ObjectName objectName = new ObjectName(JMX_TOMCAT_DATASOURCE, "type", name);
//        if (mbs.isRegistered(objectName)) {
//            mbs.unregisterMBean(objectName);
//        }
//        LOGGER.info("注册bean:{}",name);
//        mbs.registerMBean(dataSource, objectName);

//        DasTomcatDataSource dasTomcatDataSource = new DasTomcatDataSource((DalTomcatDataSource) dataSource);
//        mbs.registerMBean(dasTomcatDataSource, objectName);
    }

    public void close() {
        super.close();
    }

    public void close(boolean all) {
        super.close(all);
    }

    public int getActive() {
        return super.getActive();
    }

    @Override
    public int getIdle() {
        return super.getIdle();
    }

    @Override
    public void purge() {
        super.purge();
    }

    @Override
    public ConnectionPool getPool() {
        return super.getPool();
    }

    public ConnectionPool createPool() throws SQLException {
        if (pool != null) {
            return pool;
        } else {
            return pCreatePool();
        }
    }

    private synchronized ConnectionPool pCreatePool() throws SQLException {
        if (pool != null) {
            return pool;
        } else {
            pool = new IctTomcatConnectionPool(poolProperties);
            return pool;
        }
    }
}