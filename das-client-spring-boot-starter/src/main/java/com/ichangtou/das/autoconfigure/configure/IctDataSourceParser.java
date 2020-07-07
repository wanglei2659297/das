package com.ichangtou.das.autoconfigure.configure;

import com.ichangtou.das.properties.IctDasProperties;
import com.ichangtou.druid.IctDruidPoolPropertiesHelper;
import com.ichangtou.druid.IctDruidiPoolProperties;
import com.ichangtou.hikari.IctHikariPoolProperties;
import com.ichangtou.hikari.IctHikariPoolPropertiesHelper;
import com.ichangtou.tomcat.IctTomcatPoolProperties;
import com.ichangtou.tomcat.IctTomcatPoolPropertiesHelper;
import com.ichangtou.util.IctDataSourceUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/03 15:57
 */
public class IctDataSourceParser {

    private String globalDbUserName = null;
    private String globalDbPassword = null;
    private String globalDriverClassName = null;

    public Map<Object, Object> generatorDataSource(IctDasProperties ictDasProperties) throws SQLException {

        //全局参数
        this.globalDbUserName = ictDasProperties.getGlobal() == null ? null : ictDasProperties.getGlobal().getUserName();
        this.globalDbPassword = ictDasProperties.getGlobal() == null ? null : ictDasProperties.getGlobal().getPassword();
        this.globalDriverClassName = ictDasProperties.getGlobal() == null ? null : ictDasProperties.getGlobal().getDriverClassName();

        return dealDataSourceList(ictDasProperties);
    }


    private Map<Object, Object> dealDataSourceList(IctDasProperties ictDasProperties) throws SQLException {
        Assert.isTrue(isNotEmpty(ictDasProperties), "dataSource list can't be empty ");

        Map<Object, Object> map = new HashMap<>();

        if (CollectionUtils.isNotEmpty(ictDasProperties.getHikari())) {
            for (IctHikariPoolProperties ictHikariPoolProperties : ictDasProperties.getHikari()) {
                //替换全局参数
                if (StringUtils.isBlank(ictHikariPoolProperties.getUsername())) {
                    ictHikariPoolProperties.setUsername(globalDbUserName);
                }
                if (StringUtils.isBlank(ictHikariPoolProperties.getPassword())) {
                    ictHikariPoolProperties.setPassword(globalDbPassword);
                }
                if (StringUtils.isBlank(ictHikariPoolProperties.getDriverClassName())) {
                    ictHikariPoolProperties.setDriverClassName(globalDriverClassName);
                }

                Assert.isTrue(StringUtils.isNotBlank(ictHikariPoolProperties.getName()), "pool name can not be blank ");
                Assert.isTrue(StringUtils.isNotBlank(ictHikariPoolProperties.getUsername()), "username can not be blank ");
                Assert.isTrue(StringUtils.isNotBlank(ictHikariPoolProperties.getPassword()), "password can not be blank ");
                Assert.isTrue(StringUtils.isNotBlank(ictHikariPoolProperties.getDriverClassName()), "driverClassName can not be blank ");

                DataSource dataSource = IctDataSourceUtil.createDataSource(
                        IctHikariPoolPropertiesHelper.getInstance().convertIctProperties(ictHikariPoolProperties));
                map.put(ictHikariPoolProperties.getName(), dataSource);
            }

        }
        if (CollectionUtils.isNotEmpty(ictDasProperties.getTomcat())) {
            for (IctTomcatPoolProperties ictTomcatPoolProperties : ictDasProperties.getTomcat()) {
                //替换全局参数

                if (StringUtils.isBlank(ictTomcatPoolProperties.getUsername())) {
                    ictTomcatPoolProperties.setUsername(globalDbUserName);
                }
                if (StringUtils.isBlank(ictTomcatPoolProperties.getPassword())) {
                    ictTomcatPoolProperties.setPassword(globalDbPassword);
                }
                if (StringUtils.isBlank(ictTomcatPoolProperties.getDriverClassName())) {
                    ictTomcatPoolProperties.setDriverClassName(globalDriverClassName);
                }

                Assert.isTrue(StringUtils.isNotBlank(ictTomcatPoolProperties.getName()), "pool name can not be blank ");
                Assert.isTrue(StringUtils.isNotBlank(ictTomcatPoolProperties.getUsername()), "username can not be blank ");
                Assert.isTrue(StringUtils.isNotBlank(ictTomcatPoolProperties.getPassword()), "password can not be blank ");
                Assert.isTrue(StringUtils.isNotBlank(ictTomcatPoolProperties.getDriverClassName()), "driverClassName can not be blank ");

                DataSource dataSource = IctDataSourceUtil.createDataSource(
                        IctTomcatPoolPropertiesHelper.getInstance().convertIctProperties(ictTomcatPoolProperties)
                );
                map.put(ictTomcatPoolProperties.getName(), dataSource);
            }

        }
        if (CollectionUtils.isNotEmpty(ictDasProperties.getDruid())) {
            for (IctDruidiPoolProperties ictDruidiPoolProperties : ictDasProperties.getDruid()) {
                //替换全局参数
                if (StringUtils.isBlank(ictDruidiPoolProperties.getUsername())) {
                    ictDruidiPoolProperties.setUsername(globalDbUserName);
                }
                if (StringUtils.isBlank(ictDruidiPoolProperties.getPassword())) {
                    ictDruidiPoolProperties.setPassword(globalDbPassword);
                }
                if (StringUtils.isBlank(ictDruidiPoolProperties.getDriverClassName())) {
                    ictDruidiPoolProperties.setDriverClassName(globalDriverClassName);
                }

                Assert.isTrue(StringUtils.isNotBlank(ictDruidiPoolProperties.getName()), "pool name can not be blank ");
                Assert.isTrue(StringUtils.isNotBlank(ictDruidiPoolProperties.getUsername()), "username can not be blank ");
                Assert.isTrue(StringUtils.isNotBlank(ictDruidiPoolProperties.getPassword()), "password can not be blank ");
                Assert.isTrue(StringUtils.isNotBlank(ictDruidiPoolProperties.getDriverClassName()), "driverClassName can not be blank ");

                DataSource dataSource = IctDataSourceUtil.createDataSource(IctDruidPoolPropertiesHelper.getInstance().convertIctProperties(ictDruidiPoolProperties));
                map.put(ictDruidiPoolProperties.getName(), dataSource);
            }
        }

        return map;
    }

    private boolean isNotEmpty(IctDasProperties ictDasProperties) {
        return CollectionUtils.isNotEmpty(ictDasProperties.getHikari())
                || CollectionUtils.isNotEmpty(ictDasProperties.getTomcat())
                || CollectionUtils.isNotEmpty(ictDasProperties.getDruid());
    }
}