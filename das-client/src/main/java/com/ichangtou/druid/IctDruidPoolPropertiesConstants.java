package com.ichangtou.druid;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 18:39
 */
public class IctDruidPoolPropertiesConstants {

    public static final String name = "name";//
    public static final String jdbcUrl = "jdbcUrl";//
    public static final String initialSize = "initialSize";//	0
    public static final String maxActive = "maxActive";//8
    public static final String maxIdle = "maxIdle";//	8
    public static final String minIdle = "minIdle";//
    public static final String maxWait = "maxWait";//
    public static final String poolPreparedStatements = "poolPreparedStatements";//	false
    public static final String maxOpenPreparedStatements = "maxOpenPreparedStatements";//-1
    public static final String validationQuery = "validationQuery";//
    public static final String testOnBorrow = "testOnBorrow";//true
    public static final String testOnReturn = "testOnReturn";//	false
    public static final String testWhileIdle = "testWhileIdle";//	false
    public static final String timeBetweenEvictionRunsMillis = "timeBetweenEvictionRunsMillis";//
    public static final String minEvictableIdleTimeMillis = "minEvictableIdleTimeMillis";//
    public static final String connectionInitSqls = "connectionInitSqls";//
    public static final String exceptionSorter = "exceptionSorter";//
    public static final String filters = "filters";//
//    public static final  List<Filter> proxyFilters;//    类型是List<com.alibaba.druid.filter.Filter>，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系

}