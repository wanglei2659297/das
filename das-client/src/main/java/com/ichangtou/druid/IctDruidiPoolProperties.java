package com.ichangtou.druid;

import com.ichangtou.IctPoolProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 18:43
 */
@Setter
@Getter
public class IctDruidiPoolProperties extends IctPoolProperties {

    //=============================================================================================
    // ====================================== druid 配置 ==========================================
    //=============================================================================================
    /**
     * 初始化时建立物理连接的个数。
     */
    private int initialSize = 5;//	0
    /**
     * 最大连接池数量
     */
    private int maxActive = 100;//8
    /**
     * 最大空闲数量
     */
    private int maxIdle = 10;//	8
    /**
     * 最小连接池数量
     */
    private int minIdle = 5;//
    /**
     * 获取连接时最大等待时间，单位毫秒。
     */
    private int maxWait = 30 * 000;//

    /**
     * 是否只读
     */
    private boolean readOnly = false;

    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
     */
    private boolean poolPreparedStatements = false;//	false
    /**
     * 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
     * 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
     */
    private int maxOpenPreparedStatements = 100;//-1
    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
     */
    private String validationQuery;//
    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private boolean testOnBorrow = true;//true
    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private boolean testOnReturn = false;//	false
    /**
     * 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，
     * 如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     */
    private boolean testWhileIdle = true;//	false
    /**
     * 有两个含义：
     * 1) Destroy线程会检测连接的间隔时间
     * 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
     */
    private int timeBetweenEvictionRunsMillis = 60 * 1000;//
    /**
     * 连接在池中最小生存的时间，单位是毫秒
     */
    private int minEvictableIdleTimeMillis = 10 * 60 * 1000;//

    /***/
    private int maxPoolPreparedStatementPerConnectionSize = 10;

    /**
     * 物理连接初始化的时候执行的sql
     */
    private String connectionInitSqls;//
    /**
     * 根据dbType自动识别	当数据库抛出一些不可恢复的异常时，抛弃连接
     */
    private String exceptionSorter;//
    /**
     * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
     * 监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
     */
    private String filters;//
//    private List<Filter> proxyFilters;//    类型是List<com.alibaba.druid.filter.Filter>，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系

    private String connectProperties;

}