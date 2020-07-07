package com.ichangtou.das.properties.das;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 逻辑库对应的物理库关系
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/03 10:55
 */
@Getter
@Setter
public class IctDataBase {

    /**
     * 物理库名称
     */
    private String connectionString;
    /**
     * 主从类型,Master/Slave
     */
    private String databaseType;
    /**
     * 分片号
     * <pre>
     * 1.执行sql时，可以通过Hints指定sql必须在指定的分片上执行。
     * 例如：
     *   指定操作所在的表分片 ：dao.insert(p, hints().inTableShard("2"))
     *   指定操作所在的数据库分片 ：pk = dao.queryByPk(pk, hints().inShard("1"))
     * 2.同一个分片的主从，配置同样的值
     * </pre>
     */
    private String sharding;


}