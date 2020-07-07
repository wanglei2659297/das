package com.ichangtou.tomcat;

import com.ppdai.das.core.datasource.ConnectionListener;
import com.ppdai.das.core.datasource.DefaultConnectionListener;
import com.ppdai.das.core.helper.ServiceLoaderHelper;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PooledConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/01 18:43
 */
public class IctTomcatConnectionPool extends ConnectionPool {

    private static Logger logger = LoggerFactory.getLogger(IctTomcatConnectionPool.class);

    private static ConnectionListener connectionListener = ServiceLoaderHelper.getInstance(ConnectionListener.class, DefaultConnectionListener.class);

    public IctTomcatConnectionPool(PoolConfiguration prop) throws SQLException {
        super(prop);
    }

    @Override
    protected PooledConnection createConnection(long now, PooledConnection notUsed, String username, String password)
            throws SQLException {

        PooledConnection pooledConnection = super.createConnection(now, notUsed, username, password);
        try {
            connectionListener.onCreateConnection(getName(),
                    pooledConnection == null ? null : pooledConnection.getConnection());
        } catch (Exception e) {
            logger.error("[createConnection]" + this, e);

        }
        return pooledConnection;
    }

    @Override
    protected void release(PooledConnection con) {

        try {
            connectionListener.onReleaseConnection(getName(), con == null ? null : con.getConnection());
        } catch (Exception e) {
            logger.error("[release]" + this, e);
        }
        super.release(con);
    }

    @Override
    protected void abandon(PooledConnection con) {
        try {
            connectionListener.onAbandonConnection(getName(), con == null ? null : con.getConnection());
        } catch (Exception e) {
            logger.error("[abandon]" + this, e);
        }

        super.abandon(con);
    }

    public static void setConnectionListener(ConnectionListener connectionListener) {
        IctTomcatConnectionPool.connectionListener = connectionListener;
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
    public int getActive() {
        return super.getActive();
    }
}