package com.ppdai.das.core.client;

import com.ichangtou.IctPooledConnection;
import com.ppdai.das.client.Hints;
import com.ppdai.das.core.DasConfigureFactory;
import com.ppdai.das.core.DasException;
import com.ppdai.das.core.DasLogger;
import com.ppdai.das.core.HintEnum;

import java.sql.Connection;
import java.sql.SQLException;

public class DalConnection {
	private Integer oldIsolationLevel;
	private Integer newIsolationLevel;
	private Connection conn;
	private boolean master;
	private String shardId;
	private DbMeta meta;
	private DasLogger logger;
	private boolean needDiscard;;

	public DalConnection(Connection conn, boolean master, String shardId, DbMeta meta) throws SQLException {
		this.oldIsolationLevel = conn.getTransactionIsolation();
		this.conn = conn;
		this.master = master;
		this.shardId = shardId;
		this.meta = meta;
		this.logger = DasConfigureFactory.getLogger();
	}

	public Connection getConn() {
		return conn;
	}

	public boolean isMaster() {
		return master;
	}

	public DbMeta getMeta() {
		return meta;
	}

	public String getShardId() {
		return shardId;
	}

	public String getDatabaseName() throws SQLException {
		return meta.getDatabaseName();
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		if(conn.getAutoCommit() != autoCommit) {
            conn.setAutoCommit(autoCommit);
        }
	}

	public void applyHints(Hints hints) throws SQLException {
		Integer level = hints.getInt(HintEnum.isolationLevel);

		if(level == null || oldIsolationLevel.equals(level)) {
            return;
        }

		newIsolationLevel = level;
		conn.setTransactionIsolation(level);
	}

	public void error(Throwable e) {
		needDiscard |= isDisconnectionException(e);
	}

	public void close() {
		try {
			if(conn == null || conn.isClosed()) {
                return;
            }
		} catch (Throwable e) {
			logger.error("Restore connection isolation level failed!", e);
		}

		try {
			if(newIsolationLevel != null) {
                conn.setTransactionIsolation(oldIsolationLevel);
            }
		} catch (Throwable e) {
			logger.error("Restore connection isolation level failed!", e);
		}

		try {
			if(needDiscard) {
				markDiscard(conn);
			}

			conn.close();
		} catch (Throwable e) {
			logger.error("Close connection failed!", e);
		}
		conn = null;
	}

	private boolean isDisconnectionException(Throwable e) {
		//Filter wrapping exception
		while(e!= null && e instanceof DasException) {
			e = e.getCause();
		}

		while(e!= null && !(e instanceof SQLException)) {
			e = e.getCause();
		}

		if(e == null) {
            return false;
        }

		SQLException se = (SQLException)e;
		if(meta.getDatabaseCategory().isDisconnectionError(se.getSQLState())) {
            return true;
        }

		return isDisconnectionException(se.getNextException());
	}

	private void markDiscard(Connection conn) throws SQLException {
		IctPooledConnection pConn = (IctPooledConnection)conn.unwrap(IctPooledConnection.class);
		pConn.setDiscarded(true);
	}
}
