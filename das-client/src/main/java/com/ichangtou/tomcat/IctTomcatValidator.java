package com.ichangtou.tomcat;

import com.ichangtou.IctDataSourceValidator;
import com.ichangtou.IctPoolPropertiesHolder;
import com.ichangtou.IctPooledConnection;
import com.mysql.jdbc.MySQLConnection;
import com.ppdai.das.core.helper.MySqlConnectionHelper;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Statement;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 10:33
 */
public class IctTomcatValidator implements IctDataSourceValidator, Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(IctTomcatValidator.class);
    private static final int DEFAULT_VALIDATE_TIMEOUT_IN_SECONDS = 5;

    @Override
    public boolean validate(Connection connection, int validateAction) {
        boolean isValid = false;
        try {
            String query = null;
            int validationQueryTimeout = -1;

            if (validateAction == IctPooledConnection.VALIDATE_INIT) {
                PoolProperties poolProperties = getPoolProperties(connection);
                if (poolProperties != null) {
                    query = poolProperties.getInitSQL();
                    validationQueryTimeout = poolProperties.getValidationQueryTimeout();
                    if (validationQueryTimeout <= 0) {
                        validationQueryTimeout = DEFAULT_VALIDATE_TIMEOUT_IN_SECONDS;
                    }
                }
            }

            if (query == null) {
                if (connection instanceof MySQLConnection) {
                    MySQLConnection mySqlConnection = (MySQLConnection) connection;
                    isValid = MySqlConnectionHelper.isValid(mySqlConnection, DEFAULT_VALIDATE_TIMEOUT_IN_SECONDS);
                } else {
                    isValid = connection.isValid(DEFAULT_VALIDATE_TIMEOUT_IN_SECONDS);
                }

                if (!isValid) {
                    LOGGER.warn("isValid() returned false.");
                }
            } else {
                Statement stmt = null;
                try {
                    stmt = connection.createStatement();
                    stmt.setQueryTimeout(validationQueryTimeout);
                    stmt.execute(query);
                    isValid = true;
                } finally {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Exception ignore2) {
                            /* NOOP */
                        }
                    }
                }
            }
        } catch (Throwable ex) {
            LOGGER.warn("Datasource validation error", ex);
        }

        return isValid;
    }

    private PoolProperties getPoolProperties(Connection connection) {
        String url = null;
        String userName = null;
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            url = metaData.getURL();
            userName = metaData.getUserName();
        } catch (Throwable e) {
            LOGGER.warn("DataSourceValidator getPoolProperties error", e);
            return null;
        }

        return (PoolProperties) IctPoolPropertiesHolder.getInstance().getPoolProperties(url, userName);
    }
}