package com.ichangtou.das.properties.das;

import com.ppdai.das.core.enums.DatabaseCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @description: 逻辑库配置
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/03 10:45
 */
@Getter
@Setter
public class IctDataBaseSet {

    /**
     * 名称
     */
    private String name;
    /**
     * 支持的数据库:
     * mySqlProvider
     * sqlProvider
     * oracleProvider
     */
    private String provider;
    /**
     * 自定义分片策略
     */
    private IctShardingStrategy shardingStrategy;
    /**
     * 逻辑库与物理数据库关系
     */
    private List<IctDataBase> dataBase;
    /**
     * 是否开启mgr
     */
    private boolean mgrEnabled = false;

}