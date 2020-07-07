package com.ichangtou.tomcat;

import com.ichangtou.IctPoolProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 10:25
 */
@Getter
@Setter
public class IctTomcatPoolProperties extends IctPoolProperties {

    //=============================================================================================
    // ==================================================  tomcat配置  =============================
    //=============================================================================================
    /**
     * (String) 当连接第一次建立时执行的SQL
     */
    private String initSQL;//
    /**
     * 创建连接池默认连接数目，默认值为10
     */
    private int initialSize = 5;
    /**
     * 最大允许的连接数
     */
    private int maxActive = 100;
    /**
     * 最大等待时间，该值控制着当idle队列没有可用连接，且当前连接数量超过maxActive时就会阻塞等待idle队列有可用连接的最大等待时间
     */
    private int maxWait = 3000;
    /**
     * (int) 最大空闲连接： 连接池中容许保持空闲状态的最大连接数量， 超过的空闲连接将被释放， 如果设置为负数表示不限制
     * 如果启用，将定期检查限制连接，如果空闲时间超过minEvictableIdleTimeMillis 则释放连接 （ 参考testWhileIdle ）
     */
    private int maxIdle = 20;
    /**
     * 最小空闲连接，当idle队列数量小于minIdle时将不执行checkIdle方法
     */
    private int minIdle = 5;
    /**
     * (long)保持连接的最大毫秒数。当一个连接被归还时，连接池将检查是否满足：现在时间-连接时长>maxAge，如果条件满足，连接将被关闭而不是回到池中。默认值为0，标识禁用该功能。
     * 默认0
     */
    private long maxAge = 30 * 60 * 1000;
    /**
     * (String) 当建立新连接时被发送给JDBC 驱动的连接参数，格式必须是 [propertyName=property;]
     * *注意 ：参数user/password 将被明确传递，所以不需要包括在这里。
     */
    private String connectionProperties;
    /**
     * 校验连接有效性时使用的sql语句，比如select 1等
     */
    private String validationQuery;//
    /**
     * 有效性校验超时时间,单位秒
     */
    private int validationQueryTimeout = 3;//
    /**
     * (long) 避免过度验证，保证验证不超过这个频率——以毫秒为单位。如果一个连接应该被验证，但上次验证未达到指定间隔，将不再次验证。
     * 默认30000（30秒）
     */
    private long validationInterval = 30 * 000;

    /**
     * 是否只读
     */
    private boolean readOnly = false;

    /**
     * 当连接从busy列表移除添加到idle队列前是否需要校验连接的有效性，具体参考org.apache.tomcat.jdbc.pool.ConnectionPool类shouldClose方法
     */
    private boolean testOnReturn = false;//
    /**
     * 空闲时是否校验连接的有效性，建议设置为true，这样就可以在连接池空闲时检验所有idle连接的有效性，避免使用到无效的连接。
     */
    private boolean testWhileIdle = false;//
    /**
     * 从连接池中获取连接时是否需要验证返回连接的有效性，一般不使用，如果使用最好配合validationInterval使用，即validationInterval设置为非零值，validationInterval默认为3000毫秒
     */
    private boolean testOnBorrow = true;//
    /**
     * (String)实现org.apache.tomcat.jdbc.pool.Validator接口的类名，必须存在默认或明确的无参构造方 法。
     * 将建立一个指定类的实例作为验证器，用来代替执行查询的连接验证。例如：com.mycompany.project.SimpleValidator。
     */
    private String validatorClassName;//
    /**
     * 该参数控制着idle连接校验、释放工作的执行周期
     * 每60秒运行一次空闲连接回收器
     */
    private int timeBetweenEvictionRunsMillis = 60 * 1000;//
    /**
     * 某个连接在被释放前可以空闲的最大时间，但是该事件到了连接也不一定被释放掉，要看当前idle队列中空闲线程数是否大于minIdle值，如果小于就保留，大于则满足释放的条件
     */
    private int minEvictableIdleTimeMillis = 10 * 60 * 1000;//
    /**
     * (boolean) 是否将连接注册到JMX
     * 默认true
     */
    private boolean jmxEnabled = true;
    /**
     * (String)（jdbc拦截器——jdbc-pool的高级扩展属 性）用分号分隔的、继承org.apache.tomcat.jdbc.pool.JdbcInterceptor的类名列表。
     * 这些拦截器将被插入到对 java.sql.Connection操作之前的拦截器链上。
     * 预制的拦截器有：
     * org.apache.tomcat.jdbc.pool.interceptor.ConnectionState - 追踪自动提交、只读状态、catalog和事务隔离等级等状态。
     * （keeps track of auto commit， read only， catalog and transaction isolation level.）
     * org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer - 追踪打开的statement，当连接被归还时关闭它们。
     * （keeps track of opened statements， and closes them when the connection is returned to the pool.）
     * 更多预制拦截器详细描述请参见JDBC拦截器部分
     */
    private String jdbcInterceptors;
    /**
     * busy队列中某个连接使用时间过长，配合removeAbandoned使用
     */
    private int removeAbandonedTimeout = 180;
    /**
     * 是否释放busy队列超时的连接，配合removeAbandonedTimeout使用
     */
    private boolean removeAbandoned = false;//
    /**
     * 控制使用abandon连接时输出对应日志
     */
    private boolean logAbandoned = false;//

}