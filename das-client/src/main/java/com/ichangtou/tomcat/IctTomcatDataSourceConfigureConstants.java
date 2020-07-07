package com.ichangtou.tomcat;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 16:53
 */
public interface IctTomcatDataSourceConfigureConstants {

    public static final String URL = "url";
    public static final String INITSQL = "initSQL";//
    public static final String INITIALSIZE = "initialSize";//
    public static final String MAXACTIVE = "maxActive";//
    public static final String MAXWAIT = "maxWait";//
    public static final String MAXIDLE = "maxIdle";
    public static final String MINIDLE = "minIdle";//
    public static final String MAXAGE = "maxAge";
    public static final String CONNECTIONPROPERTIES = "connectionProperties";
    public static final String VALIDATIONQUERY = "validationQuery";//
    public static final String VALIDATIONQUERYTIMEOUT = "validationQueryTimeout";//
    public static final String VALIDATIONINTERVAL = "validationInterval";
    public static final String TESTONRETURN = "testOnReturn";//
    public static final String TESTWHILEIDLE = "testWhileIdle";//
    public static final String TESTONBORROW = "testOnBorrow";//
    public static final String VALIDATORCLASSNAME = "validatorClassName";//
    public static final String TIMEBETWEENEVICTIONRUNSMILLIS = "timeBetweenEvictionRunsMillis";//
    public static final String MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";//
    public static final String JMXENABLED = "jmxEnabled";
    public static final String JDBCINTERCEPTORS = "jdbcInterceptors";
    public static final String REMOVEABANDONEDTIMEOUT = "removeAbandonedTimeout";
    public static final String REMOVEABANDONED = "removeAbandoned";//
    public static final String LOGABANDONED = "logAbandoned";//

}