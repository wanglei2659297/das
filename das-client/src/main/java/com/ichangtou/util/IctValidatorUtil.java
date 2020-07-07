package com.ichangtou.util;

import com.ichangtou.IctDataSourceValidator;
import com.ichangtou.tomcat.IctTomcatValidator;
import com.ppdai.das.core.configure.DataSourceConfigureConstants;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 11:38
 */
public class IctValidatorUtil {

    public static IctDataSourceValidator createValidator() {

        String dataSourceType = IctDataSourceTypeUtil.getDataSourceType();

        switch (dataSourceType) {
            case DataSourceConfigureConstants.DATASOURCE_CLASS_HIKARI:
                return null;
            case DataSourceConfigureConstants.DATASOURCE_CLASS_TOMCAT:
                return new IctTomcatValidator();
            case DataSourceConfigureConstants.DATASOURCE_CLASS_DRUID:
                return null;
            default:
                throw new RuntimeException("no PoolPropertiesHolder class(hikari,tomcat,druid) exist !");
        }
    }

}