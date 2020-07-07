package com.ichangtou.util;

import com.ppdai.das.core.configure.DataSourceConfigureConstants;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 14:55
 */
public class IctDataSourceTypeUtil {

    /**
     * 判断数据连接池类型
     */
    public static String getDataSourceType() {
        //hikari dataSource
        if (IctClassUtil.classExist(DataSourceConfigureConstants.DATASOURCE_CLASS_HIKARI)) {
            return DataSourceConfigureConstants.DATASOURCE_CLASS_HIKARI;
        }

        //tomcat dataSource
        if (IctClassUtil.classExist(DataSourceConfigureConstants.DATASOURCE_CLASS_TOMCAT)) {
            return DataSourceConfigureConstants.DATASOURCE_CLASS_TOMCAT;

        }

        //druid dataSource
        if (IctClassUtil.classExist(DataSourceConfigureConstants.DATASOURCE_CLASS_DRUID)) {
            return DataSourceConfigureConstants.DATASOURCE_CLASS_DRUID;

        }

        throw new RuntimeException("no dataSource class(hikari,tomcat,druid) exist !");
    }

}