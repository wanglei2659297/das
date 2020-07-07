package com.ichangtou.util;

import com.ichangtou.IctPoolPropertiesHolder;
import com.ichangtou.druid.IctDruidPoolPropertiesHolder;
import com.ichangtou.hikari.IctHikariPoolPropertiesHolder;
import com.ichangtou.tomcat.IctTomcatPoolPropertiesHolder;
import com.ppdai.das.core.configure.DataSourceConfigureConstants;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/03 10:10
 */
public class IctPoolPropertiesHolderUtil {


    public static IctPoolPropertiesHolder getIctPoolPropertiesHolder() {
        String dataSourceType = IctDataSourceTypeUtil.getDataSourceType();
        switch (dataSourceType) {
            case DataSourceConfigureConstants.DATASOURCE_CLASS_HIKARI:
                return new IctHikariPoolPropertiesHolder();
            case DataSourceConfigureConstants.DATASOURCE_CLASS_TOMCAT:
                return new IctTomcatPoolPropertiesHolder();
            case DataSourceConfigureConstants.DATASOURCE_CLASS_DRUID:
                return new IctDruidPoolPropertiesHolder();
            default:
                throw new RuntimeException("no PoolPropertiesHolder class(hikari,tomcat,druid) exist !");
        }
    }
}