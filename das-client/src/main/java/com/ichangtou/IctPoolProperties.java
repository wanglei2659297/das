package com.ichangtou;

import lombok.Data;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/01 19:02
 */
@Data
public class IctPoolProperties {

    /**
     * 连接池名称
     */
    private String name;

    /**
     * 连接数据库的用户名
     */
    private String username;
    /**
     * 连接数据库的密码。
     */
    private String password;
    /**
     * 驱动类
     */
    private String driverClassName;
    /**
     * 连接数据库的url，不同数据库不一样。
     * 例如：
     * //mysql : jdbc:mysql://10.20.153.104:3306/druid2
     * //oracle : jdbc:oracle:thin:@10.20.149.85:1521:ocnauto
     */
    private String url;//

}