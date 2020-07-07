package com.ichangtou.das.locator;

import com.ichangtou.das.provider.IctDataSourceConfigureProvider;
import com.ppdai.das.core.ConnectionLocator;
import com.ppdai.das.core.configure.DataSourceConfigureProvider;

import java.sql.Connection;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/06 18:31
 */
public class IctConnectionLocator implements ConnectionLocator {

    private IctDataSourceConfigureProvider ictDataSourceConfigureProvider;
    private IctDataSourceLocator ictDataSourceLocator;

    public IctConnectionLocator(IctDataSourceConfigureProvider ictDataSourceConfigureProvider, IctDataSourceLocator ictDataSourceLocator) {
        this.ictDataSourceConfigureProvider = ictDataSourceConfigureProvider;
        this.ictDataSourceLocator = ictDataSourceLocator;
    }

    @Override
    public void setup(Set<String> dbNames) {

    }

    @Override
    public Connection getConnection(String name) throws Exception {
        return ictDataSourceLocator.getDataSource(name).getConnection();
    }

    @Override
    public DataSourceConfigureProvider getProvider() {
        return ictDataSourceConfigureProvider;
    }

    @Override
    public void initialize(Map<String, String> settings) throws Exception {

    }
}