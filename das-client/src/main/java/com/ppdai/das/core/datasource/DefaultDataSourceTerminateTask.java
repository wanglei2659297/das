package com.ppdai.das.core.datasource;

import com.ichangtou.IctDataSource;
import com.ppdai.das.core.configure.DataSourceConfigure;
import com.ppdai.das.core.configure.DataSourceConfigureConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DefaultDataSourceTerminateTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDataSourceTerminateTask.class);

    private static final int MAX_RETRY_TIMES = 3;
    private static final int FIXED_DELAY = 5 * 1000; // milliseconds

    private String name;
    private DataSource dataSource;
    private DataSourceConfigure dataSourceConfigure;
    private Date enqueueTime;
    private int retryTimes;

    private boolean isForceClosing = false;

    public DefaultDataSourceTerminateTask(SingleDataSource oldDataSource) {
        this.name = oldDataSource.getName();
        this.dataSource = oldDataSource.getDataSource();
        this.dataSourceConfigure = oldDataSource.getDataSourceConfigure();
        this.enqueueTime = new Date();
        this.retryTimes = 0;
    }

    @Override
    public void run() {
        boolean success = closeDataSource(dataSource);
        if (success) {
            log(name, isForceClosing, enqueueTime.getTime());
            return;
        } else {
            try {
                TimeUnit.MILLISECONDS.sleep(FIXED_DELAY);
            } catch (InterruptedException e) {
                //Ignore interrupt
            }
            run();
        }
    }

    public void log(String dataSourceName, boolean isForceClosing, long startTimeMilliseconds) {
        long cost = getElapsedMilliSeconds();
        LOGGER.info(String.format("**********DataSource %s has been closed,cost:%s ms.**********", name, cost));
    }

    private boolean closeDataSource(DataSource dataSource) {
        LOGGER.info(String.format("**********Trying to close datasource %s.**********", name));
        boolean success = true;

        try {
            // Tomcat DataSource
            if (dataSource instanceof IctDataSource) {
                success = closeTomcatDataSource();
            }
        } catch (Throwable e) {
            LOGGER.warn(e.getMessage(), e);
            retryTimes++;
            success = false;
        }

        return success;
    }

    private boolean closeTomcatDataSource() {
        boolean success = true;

        LOGGER.info(String.format("Error retry times for datasource %s:%s", name, retryTimes));

        int abandonedTimeout = getAbandonedTimeout();
        LOGGER.info(String.format("Abandoned timeout for datasource %s:%s", name, abandonedTimeout));

        int elapsedSeconds = getElapsedSeconds();
        LOGGER.info(String.format("Elapsed seconds for datasource %s:%s", name, elapsedSeconds));

        IctDataSource ds = (IctDataSource) dataSource;
        if (retryTimes > MAX_RETRY_TIMES) {
            LOGGER.info(String.format("Force closing datasource %s,retry times:%s,max retry times:%s.", name,
                    retryTimes, MAX_RETRY_TIMES));
            ds.close(true);
            isForceClosing = true;
            return success;
        } else if (elapsedSeconds >= abandonedTimeout) {
            LOGGER.info(String.format("Force closing datasource %s,elapsed seconds:%s,abandoned timeout:%s.", name,
                    elapsedSeconds, abandonedTimeout));
            ds.close(true);
            isForceClosing = true;
            return success;
        }

        Object pool = ds.getPool();
        if (pool == null) {
            return success;
        }

        int idle = ds.getIdle();
        if (idle > 0) {
            ds.purge();
            LOGGER.info(String.format("Idle connections of datasource %s have been closed.", name));
        }

        int active = ds.getActive();
        if (active == 0) {
            ds.close();
            LOGGER.info(
                    String.format("Active connections of datasource %s is zero, datasource has been closed.", name));
        } else if (active > 0) {
            LOGGER.info(String.format("Active connections of datasource %s is %s.", name, active));
            success = false;
        }

        return success;
    }

    private int getAbandonedTimeout() {
        return dataSourceConfigure.getIntProperty(DataSourceConfigureConstants.REMOVEABANDONEDTIMEOUT,
                DataSourceConfigureConstants.DEFAULT_REMOVEABANDONEDTIMEOUT);
    }

    private long getElapsedMilliSeconds() {
        return System.currentTimeMillis() - enqueueTime.getTime();
    }

    private int getElapsedSeconds() {
        long elapsed = getElapsedMilliSeconds();
        return (int) (elapsed / 1000);
    }

}
