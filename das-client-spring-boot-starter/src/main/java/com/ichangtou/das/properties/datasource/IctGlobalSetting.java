package com.ichangtou.das.properties.datasource;

import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/07 14:44
 */
@Getter
@Setter
public class IctGlobalSetting {

    /**
     * 全局的数据库账号
     * 配置此账号后，可以不用单独配置每个数据源的账号
     * 此处的配置优先级低于单独数据库的配置
     */
    private String userName;
    /**
     * 全局的数据库密码
     * 配置此密码后，可以不用单独配置每个数据源的密码
     * 此处的配置优先级低于单独数据库的配置
     */
    private String password;

    /**
     * 全局的数驱动类配置
     * 配置此驱动后，可以不用单独配置每个数据源的驱动
     * 此处的配置优先级低于单独数据库的配置
     */
    private String driverClassName;

}