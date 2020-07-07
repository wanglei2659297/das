package com.ichangtou.util;

import com.ichangtou.tomcat.IctTomcatPoolPropertiesHelper;
import com.zaxxer.hikari.HikariConfig;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/03 10:17
 */
public class IctPoolPropertiesUtil {

    public static Object mergePoolProperties(Object propObj, String mgrUrl) {

        if (propObj instanceof PoolProperties) {
            PoolProperties poolProperties = (PoolProperties) propObj;
            poolProperties = IctTomcatPoolPropertiesHelper.getInstance().copy(poolProperties);
            poolProperties.setMinIdle(0);
            poolProperties.setMinEvictableIdleTimeMillis(1000);
            poolProperties.setUrl(mgrUrl);

            return poolProperties;

        } else if (propObj instanceof HikariConfig) {
            return propObj;
        }

        return propObj;
    }

}