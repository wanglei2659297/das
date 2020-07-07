package com.ichangtou.tomcat;

import com.ichangtou.IctPooledConnection;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PooledConnection;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 11:21
 */
public class IctTomcatPooledConnection extends PooledConnection implements IctPooledConnection {
    /**
     * Constructor
     *
     * @param prop   - pool properties
     * @param parent - the parent connection pool
     */
    public IctTomcatPooledConnection(PoolConfiguration prop, ConnectionPool parent) {
        super(prop, parent);
    }

    @Override
    public void setDiscarded(boolean discarded) {
        super.setDiscarded(discarded);
    }
}