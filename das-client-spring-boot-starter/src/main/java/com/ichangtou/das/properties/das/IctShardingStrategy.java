package com.ichangtou.das.properties.das;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Properties;

/**
 * @description: 分片策略
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/03 10:50
 */
@Getter
@Setter
public class IctShardingStrategy {

    /**
     * 分片策略类名
     * 该类必须要集成{@link com.ppdai.das.strategy.AbstractConditionStrategy}类
     */
    private String className;

    // ==========================================
    //自定义策略需要用到的参数
    // ==========================================
    private Properties customerProps;

    // ==========================================
    // AbstractConditionStrategy类需要用到的参数
    // ==========================================
    /**
     * 是否开启分库
     * <pre>
     * true:分库
     * false:不分库
     * 只分表不分库的话，mod 和 columns 不需要配。
     * </pre>
     */
    private boolean shardByDb = false;
    /**
     * 是否开启分表
     * <pre>
     * true:分表
     * false:不分表
     * 只分库不分表的话，tableMod 和 tableColumns 不需要配
     * </pre>
     */
    private boolean shardByTable = false;
    /**
     * 分表的表名
     */
    private List<String> shardedTables;
    /**
     * 分库总数
     */
    private int mod = 1;
    /**
     * 分表总数
     */
    private int tableMod = 1;
    /**
     * 分库取模的列名字
     */
    private String columns;
    /**
     * 分表取模的列名字
     */
    private String tableColumns;
}