package com.ichangtou;

import com.ichangtou.util.IctPoolPropertiesHolderUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 09:56
 */
public abstract class IctPoolPropertiesHolder {

    protected static final Object LOCK = new Object();
    protected static final Object LOCK2 = new Object();
    protected static final String SEMICOLON = ";";
    protected static final String AT = "@";

    protected static ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> poolPropertiesMap =
            new ConcurrentHashMap<>();
    private static IctPoolPropertiesHolder holder = null;

    public synchronized static IctPoolPropertiesHolder getInstance() {
        if (holder == null) {
            holder = IctPoolPropertiesHolderUtil.getIctPoolPropertiesHolder();
        }
        return holder;
    }

    public abstract void setPoolProperties(Object ictPoolProperties);

    public Object getPoolProperties(String url, String userName) {
        if (url == null || url.length() == 0) {
            return null;
        }
        if (userName == null || userName.length() == 0) {
            return null;
        }

        url = getShortString(url, SEMICOLON);
        userName = getShortString(userName, AT);
        ConcurrentHashMap<String, Object> map = poolPropertiesMap.get(url);
        if (map == null) {
            return null;
        }
        return map.get(userName);
    }

    protected String getShortString(String str, String separator) {
        if (str == null || str.length() == 0) {
            return null;
        }
        int index = str.indexOf(separator);
        if (index > -1) {
            str = str.substring(0, index);
        }
        return str;
    }
}