package com.ichangtou.tomcat;

import com.ichangtou.IctPoolProperties;
import com.ichangtou.IctPoolPropertiesHolder;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 09:56
 */
public class IctTomcatPoolPropertiesHolder extends IctPoolPropertiesHolder {

    public void setPoolProperties(Object poolProp) {
        PoolProperties poolProperties = (PoolProperties) poolProp;
        if (poolProperties == null) {
            return;
        }

        String url = poolProperties.getUrl();
        if (url == null || url.length() == 0) {
            return;
        }

        String userName = poolProperties.getUsername();
        if (userName == null || userName.length() == 0) {
            return;
        }

        url = super.getShortString(url, SEMICOLON);
        userName = getShortString(userName, AT);
        ConcurrentHashMap<String, Object> map = poolPropertiesMap.get(url);

        if (map == null) {
            synchronized (LOCK) {
                map = poolPropertiesMap.get(url);
                if (map == null) {
                    map = new ConcurrentHashMap<>();
                    poolPropertiesMap.put(url, map);
                }
            }
        }

        /*
         * if (!map.containsKey(userName)) { synchronized (LOCK2) { if (!map.containsKey(userName)) { map.put(userName,
         * poolProperties); } } }
         */

        // avoid caching for InitSQL
        synchronized (LOCK2) {
            map.put(userName, poolProperties);
        }
    }
}