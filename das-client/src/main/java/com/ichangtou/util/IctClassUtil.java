package com.ichangtou.util;

import org.apache.commons.lang.ClassUtils;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/01 11:07
 */
public class IctClassUtil {

    public static boolean classExist(String classFullName) {
        try {
            ClassUtils.getClass(classFullName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}