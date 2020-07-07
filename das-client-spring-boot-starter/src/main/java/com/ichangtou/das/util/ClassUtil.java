package com.ichangtou.das.util;

import org.apache.commons.lang3.ClassUtils;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/01 11:07
 */
public class ClassUtil {

    public boolean classExist(String classFullName) {
        try {
            ClassUtils.getClass(classFullName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}