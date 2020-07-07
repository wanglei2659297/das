package com.ichangtou.das.autoconfigure.configure;

import com.ichangtou.das.properties.IctDasProperties;
import com.ichangtou.das.properties.das.IctDataBase;
import com.ichangtou.das.properties.das.IctDataBaseSet;
import com.ichangtou.das.properties.das.IctShardingStrategy;
import com.ppdai.das.core.DataBase;
import com.ppdai.das.core.DatabaseSet;
import com.ppdai.das.core.configure.DalConfigConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @description: 配置转换类，将自动配置的参数转化成das的配置
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/03 10:44
 */
public class IctDasConfigureFactory {

    private static final String CLASS = "class";
    private static final String ENTRY_SEPARATOR = ";";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final List<String> SPECIAL_STR = Arrays.asList(ENTRY_SEPARATOR, ",");

    public static Map<String, DatabaseSet> convertDatabaseSet(IctDasProperties ictDasProperties) throws Exception {
        if (CollectionUtils.isEmpty(ictDasProperties.getDataBaseSet())) {
            throw new RuntimeException("");
        }

        Map<String, DatabaseSet> databaseSetMap = new HashMap<>();

        for (IctDataBaseSet ictDataBaseSet : ictDasProperties.getDataBaseSet()) {
            Assert.isTrue(StringUtils.isNotBlank(ictDasProperties.getName()), "dataset name can't be blank ");
            databaseSetMap.put(ictDataBaseSet.getName(), dealDataBaseSet(ictDataBaseSet));
        }

        return databaseSetMap;
    }

    private static DatabaseSet dealDataBaseSet(IctDataBaseSet ictDataBaseSet) throws Exception {

        Assert.isTrue(StringUtils.isNotBlank(ictDataBaseSet.getName()), "dataBaseSet name can't be empty");
        Assert.isTrue(StringUtils.isNotBlank(ictDataBaseSet.getProvider()), "dataBaseSet provider can't be empty");

        DatabaseSet databaseSet = null;

        Map<String, DataBase> dataBaseMap = dealDatabaseMap(ictDataBaseSet.getDataBase());

        //没有分片策略
        if (ictDataBaseSet.getShardingStrategy() == null) {

            databaseSet = new DatabaseSet(ictDataBaseSet.getName(),
                    ictDataBaseSet.getProvider(),
                    dataBaseMap,
                    ictDataBaseSet.isMgrEnabled());

        } else {

            databaseSet = new DatabaseSet(ictDataBaseSet.getName(),
                    ictDataBaseSet.getProvider(),
                    buildShardStrategyStr(ictDataBaseSet.getShardingStrategy())
                    , dataBaseMap,
                    ictDataBaseSet.isMgrEnabled());

        }

        //mgr
        databaseSet.setMgrReadWriteSplittingEnabled(ictDataBaseSet.isMgrEnabled());

        return databaseSet;
    }

    private static String buildShardStrategyStr(IctShardingStrategy ictShardingStrategy) {
        StringBuilder build = new StringBuilder();

        //class
        Assert.isTrue(!containSpecialStr(ictShardingStrategy.getClassName()), ictShardingStrategy.getClassName() + "'s parameter[className] can't contain special string ';' and ','");
        build.append(CLASS).append(KEY_VALUE_SEPARATOR).append(ictShardingStrategy.getClassName());

        //shardByDb
        if (ictShardingStrategy.isShardByDb()) {
            //校验参数
            Assert.isTrue(ictShardingStrategy.getMod() > 0, ictShardingStrategy.getClassName() + "'s parameter[mod] must gather than 0 ");
            Assert.isTrue(StringUtils.isNotBlank(ictShardingStrategy.getColumns()), ictShardingStrategy.getClassName() + "'s parameter[cloumns] can't be blank ");

            Assert.isTrue(!containSpecialStr(ictShardingStrategy.getColumns()), ictShardingStrategy.getClassName() + "'s parameter[cloumns] can't contain special string ';' and ','");

            //拼接参数
            build.append(ENTRY_SEPARATOR).append("shardByDb").append(KEY_VALUE_SEPARATOR).append(ictShardingStrategy.isShardByDb());
            build.append(ENTRY_SEPARATOR).append("mod").append(KEY_VALUE_SEPARATOR).append(ictShardingStrategy.getMod());
            build.append(ENTRY_SEPARATOR).append("columns").append(KEY_VALUE_SEPARATOR).append(ictShardingStrategy.getColumns());
        }
        //shardByTable
        if (ictShardingStrategy.isShardByTable()) {
            //校验参数
            Assert.isTrue(ictShardingStrategy.getTableMod() > 0, ictShardingStrategy.getClassName() + "'s parameter[tableMod] must gather than 0 ");
            Assert.isTrue(StringUtils.isNotBlank(ictShardingStrategy.getTableColumns()), ictShardingStrategy.getClassName() + "'s parameter[tableColumns] can't be blank ");
            Assert.isTrue(CollectionUtils.isNotEmpty(ictShardingStrategy.getShardedTables()), ictShardingStrategy.getClassName() + "'s parameter[shardedTables] can't be empty ");

            Assert.isTrue(!containSpecialStr(ictShardingStrategy.getShardedTables()), ictShardingStrategy.getClassName() + "'s parameter[shardedTables] can't contain special string ';' and ',' ");
            Assert.isTrue(!containSpecialStr(ictShardingStrategy.getTableColumns()), ictShardingStrategy.getClassName() + "'s parameter[tableColumns] can't contain special string ';' and ',' ");

            //拼接参数
            build.append(ENTRY_SEPARATOR).append("shardByTable").append(KEY_VALUE_SEPARATOR).append(ictShardingStrategy.isShardByTable());
            build.append(ENTRY_SEPARATOR).append("shardedTables").append(KEY_VALUE_SEPARATOR).append(StringUtils.join(ictShardingStrategy.getShardedTables(), ","));
            build.append(ENTRY_SEPARATOR).append("tableMod").append(KEY_VALUE_SEPARATOR).append(ictShardingStrategy.getTableMod());
            build.append(ENTRY_SEPARATOR).append("tableColumns").append(KEY_VALUE_SEPARATOR).append(ictShardingStrategy.getTableColumns());
        }

        //特殊的策略参数
        if (MapUtils.isNotEmpty(ictShardingStrategy.getCustomerProps())) {
            Iterator<Object> iterator = ictShardingStrategy.getCustomerProps().keySet().iterator();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                Object value = ictShardingStrategy.getCustomerProps().get(key);
                if (value != null) {
                    String keyStr = key.toString();
                    String valueStr = value.toString();

                    //不能为空且不能包含分号
                    Assert.isTrue(StringUtils.isNotEmpty(keyStr), " shardingStrategy's key can't be empty");
                    Assert.isTrue(!containSpecialStr(keyStr), " shardingStrategy's key and value both can't contain special string ';' and ',' ");
                    Assert.isTrue(StringUtils.isNotEmpty(valueStr), " shardingStrategy's value can't be empty");
                    Assert.isTrue(!containSpecialStr(valueStr), " shardingStrategy's key and value both can't contain special string ';' and ','");

                    build.append(ENTRY_SEPARATOR).append(keyStr).append(KEY_VALUE_SEPARATOR).append(valueStr);
                }
            }
        }

        return build.toString();
    }

    private static boolean containSpecialStr(List<String> list) {
        for (String s : list) {
            containSpecialStr(s);
        }
        return false;
    }

    private static boolean containSpecialStr(String s) {
        return StringUtils.isBlank(s) || SPECIAL_STR.contains(s);
    }

    private static Map<String, DataBase> dealDatabaseMap(List<IctDataBase> ictDataBaseList) {

        Assert.isTrue(CollectionUtils.isNotEmpty(ictDataBaseList), " database can't be empty");

        Map<String, DataBase> map = new HashMap<>();

        for (IctDataBase ictDataBase : ictDataBaseList) {

            Assert.isTrue(StringUtils.isNotBlank(ictDataBase.getConnectionString()), "connectionString can't be blank");

            map.put(ictDataBase.getConnectionString(),
                    new DataBase(
                            ictDataBase.getConnectionString(),
                            DalConfigConstants.MASTER.equalsIgnoreCase(ictDataBase.getDatabaseType()) ? true : false,
                            ictDataBase.getSharding(),
                            ictDataBase.getConnectionString()));
        }

        return map;
    }

}