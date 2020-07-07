package com.ichangtou;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 09:37
 */
public interface IctPooledConnection {

    //TODO 待确认数值
    public static final int VALIDATE_INIT = 4;

    void setDiscarded(boolean flag);
}