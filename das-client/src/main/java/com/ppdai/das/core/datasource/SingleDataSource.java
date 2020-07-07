package com.ppdai.das.core.datasource;

import com.ichangtou.IctDataSource;
import com.ichangtou.hikari.IctHikariDataSource;
import com.ichangtou.tomcat.IctTomcatDataSource;
import com.ichangtou.util.IctDataSourceTypeUtil;
import com.ppdai.das.core.configure.DataSourceConfigure;
import com.ppdai.das.core.configure.DataSourceConfigureConstants;
import com.ppdai.das.core.helper.ConnectionPhantomReferenceCleaner;
import com.ppdai.das.core.helper.DefaultConnectionPhantomReferenceCleaner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SingleDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleDataSource.class);
    private String name;
    private DataSourceConfigure dataSourceConfigure;
    private DataSource dataSource;

    //hikari,tomcat,druid
    private String dataSourceType;

    private static ConnectionPhantomReferenceCleaner connectionPhantomReferenceCleaner = new DefaultConnectionPhantomReferenceCleaner();
    private static AtomicBoolean containsMySQL = new AtomicBoolean(false);
    private static final String MYSQL_URL_PREFIX = "jdbc:mysql://";

    public String getName() {
        return name;
    }

    public DataSourceConfigure getDataSourceConfigure() {
        return dataSourceConfigure;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public SingleDataSource(String name, DataSourceConfigure dataSourceConfigure) throws SQLException {
        if (dataSourceConfigure == null) {
            throw new SQLException("Can not find any connection configure for " + name);
        }

        try {
            this.name = name;
            this.dataSourceConfigure = dataSourceConfigure;

            this.dataSourceType = IctDataSourceTypeUtil.getDataSourceType();

            String message = null;
            IctDataSource ictDataSource = null;
            //TODO hikari datasource
            if (DataSourceConfigureConstants.DATASOURCE_CLASS_HIKARI.equalsIgnoreCase(dataSourceType)) {
                ictDataSource = new IctHikariDataSource();

                //TODO druid datasource
            } else if (DataSourceConfigureConstants.DATASOURCE_CLASS_DRUID.equalsIgnoreCase(dataSourceType)) {
//                ictDataSource = new IctDruidDataSource();

                //tomcat datasource
            } else if (DataSourceConfigureConstants.DATASOURCE_CLASS_TOMCAT.equalsIgnoreCase(dataSourceType)) {
                ictDataSource = new IctTomcatDataSource();
            }

            this.dataSource = ictDataSource.createDatasource(name, dataSourceConfigure);
            LOGGER.info(message);
        } catch (Throwable e) {
            LOGGER.error(String.format("Error creating pool for data source %s", name), e);
        }

        try {
            if (!containsMySQL.get()) {
                if (dataSourceConfigure.getConnectionUrl().startsWith(MYSQL_URL_PREFIX)) {
                    connectionPhantomReferenceCleaner.start();
                    containsMySQL.set(true);
                }
            }
        } catch (Throwable e) {
            LOGGER.error(String.format("Error starting pool connectionPhantomReferenceCleaner"), e);
        }
    }

    private void testConnection(DataSource dataSource) throws SQLException {
        if (dataSource == null) {
            return;
        }

        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Throwable e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

}
