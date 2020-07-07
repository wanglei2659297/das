package com.ichangtou.das.locator;

import com.ichangtou.das.autoconfigure.configure.IctDataSourceParser;
import com.ichangtou.das.properties.IctDasProperties;
import com.ichangtou.das.util.IctDasThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/06 18:34
 */
@Slf4j
public class IctDataSourceLocator extends AbstractRoutingDataSource {

    public IctDataSourceLocator(IctDasProperties ictDasProperties) {
        Map<Object, Object> map = null;
        try {
            map = new IctDataSourceParser().generatorDataSource(ictDasProperties);
        } catch (SQLException e) {
            log.error("generator dataSource exception", e);
            throw new RuntimeException(e);
        }
        this.setTargetDataSources(map);
    }

    public DataSource getDataSource(String name) {
        IctDasThreadLocal.setName(name);
        return determineTargetDataSource();
    }

    protected DataSource determineTargetDataSource() {
        try {
            return super.determineTargetDataSource();
        } finally {
            IctDasThreadLocal.clear();
        }
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return IctDasThreadLocal.getName();
    }
}