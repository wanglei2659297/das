package com.ichangtou.hikari;

import com.ichangtou.IctPoolProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: hikari配置
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 13:50
 */
@Getter
@Setter
public class IctHikariPoolProperties extends IctPoolProperties {

    //=============================================================================================
    // ====================================== hikari配置 ==========================================
    //=============================================================================================
    /**
     * 连接池是否可以被jmx挂起，如果连接池被挂起，getConnection就不会超时，默认值false。
     */
    private boolean allowPoolSuspension = false;
    /**
     * connection是否自动提交，默认值true。
     */
    private boolean autoCommit = true;
    /**
     * 配置连接的数据库。
     */
    private String catalog;
    /**
     * 创建连接前默认执行的sql语句，如果语句执行失败连接则失败，然后重试连接，默认值无。
     */
    private String connectionInitSql;
    /**
     * jdbc4以下版本的驱动可以设置这个参数，用来支持jdbc4新提供的api，Connection.isValid()，默认值无。
     */
    private String connectionTestQuery;
    /**
     * 从池中获取连接的最大等待时间，超时抛异常，默认值30s。
     */
    private int connectionTimeout = 30 * 1000;
    /**
     * 设置datasource jdbc驱动提供的类名，如果基于jdbc url的jdbc驱动则不须要设置，默认值无。
     */
    private String dataSourceClassName;
    /**
     * 默认值无。
     */
    private String dataSourceJNDI;
    /**
     * datasource的properties。
     */
    private String dataSourceProperties;
    /**
     * 连接在池中闲置超过这个时间，则删除。0表示空闲连接不删除，默认值10分钟。
     */
    private int idleTimeout = 10 * 60 * 1000;
    /**
     * 连接池初始连接的timeout值，单位毫秒。如果在timeout的期间内无法初始化成功连接池，则抛出异常。
     * 如果设置为0，会尝试重连，重连失败则抛异常终止连接池，小于0表示不重连，默认值1。
     */
    private int initializationFailTimeout = 3000;
    /**
     * 是否将连接池的查询封装在自己的事务中，这个属性在autoCommit=false时生效，默认false。
     */
    private boolean isolateInternalQueries = false;

    /**
     * 内存泄漏侦测入口，默认值0。
     */
    private int leakDetectionThreshold = 0;
    /**
     * 连接最大存活时间。
     */
    private int maxLifetime = 30 * 60 * 1000;
    /**
     * 最大的缓冲池大小。
     */
    private int maximumPoolSize = 100;
    /**
     * 最小空闲的连接数。
     */
    private int minimumIdle = 5;
    /**
     * 连接池名，给JMX用，默认值自动生成。
     */
    private String poolName;
    /**
     * 连接是否只读，默认值false。
     */
    private boolean readOnly = false;
    /**
     * 是否注册管理构建工具，默认值false。
     */
    private boolean registerMbeans = false;
    /**
     * 事务的隔离级别，默认驱动默认值。
     */
    private String transactionIsolation;
    /**
     * 测试连接活动的超时时间，必须比connectionTimeout小，默认250ms。
     */
    private int validationTimeout = 250;

}