package com.ichangtou.hikari;

import com.ichangtou.IctDataSourceValidator;

import java.sql.Connection;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/02 13:50
 */
public class IctHikariValidator implements IctDataSourceValidator {
    @Override
    public boolean validate(Connection connection, int validateAction) {
        return true;
    }
}