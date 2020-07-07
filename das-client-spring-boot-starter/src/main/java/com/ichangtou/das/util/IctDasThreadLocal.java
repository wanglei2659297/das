package com.ichangtou.das.util;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/01 15:36
 */
public class IctDasThreadLocal {

    private static ThreadLocal<String> local = new ThreadLocal<>();

    public static void setName(String name) {
        local.set(name);
    }

    public static String getName() {
        return local.get();
    }

    public static void clear() {
        local.remove();
    }
}