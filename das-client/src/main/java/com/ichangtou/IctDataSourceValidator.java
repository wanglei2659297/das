package com.ichangtou;

import java.sql.Connection;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 10:32
 */
public interface IctDataSourceValidator {
    boolean validate(Connection connection, int validateAction);
}