package com.ichangtou.das.locator;

import com.ppdai.das.core.DataSourceConfigureLocator;
import com.ppdai.das.core.configure.ConnectionString;
import com.ppdai.das.core.configure.DataSourceConfigure;
import com.ppdai.das.core.configure.PoolPropertiesConfigure;
import com.ppdai.das.core.enums.IPDomainStatus;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 将配置缓存到内存里
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/03 11:17
 */
public class IctDataSourceConfigureLocator implements DataSourceConfigureLocator {

    // user datasource.xml pool properties configure
    private Map<String, PoolPropertiesConfigure> userPoolPropertiesConfigure = new ConcurrentHashMap<>();

    private Map<String, DataSourceConfigure> dataSourceConfiguresCache = new ConcurrentHashMap<>();

    private Map<String, ConnectionString> connectionStrings = new ConcurrentHashMap<>();

    @Override
    public void addUserPoolPropertiesConfigure(String name, PoolPropertiesConfigure configure) {

    }

    @Override
    public PoolPropertiesConfigure getUserPoolPropertiesConfigure(String name) {
        return null;
    }

    @Override
    public DataSourceConfigure getDataSourceConfigure(String name) {
        return null;
    }

    @Override
    public void addDataSourceConfigureKeySet(Set<String> names) {

    }

    @Override
    public Set<String> getDataSourceConfigureKeySet() {
        return null;
    }

    @Override
    public void setIPDomainStatus(IPDomainStatus status) {

    }

    @Override
    public IPDomainStatus getIPDomainStatus() {
        return null;
    }

    @Override
    public void setConnectionStrings(Map<String, ConnectionString> map) {

    }

    @Override
    public void setPoolProperties(PoolPropertiesConfigure configure) {

    }

    @Override
    public DataSourceConfigure mergeDataSourceConfigure(ConnectionString connectionString) {
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}