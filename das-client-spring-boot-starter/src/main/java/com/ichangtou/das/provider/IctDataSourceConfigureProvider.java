package com.ichangtou.das.provider;

import com.ichangtou.das.properties.IctDasProperties;
import com.ppdai.das.core.DasConfigure;
import com.ppdai.das.core.configure.DataSourceConfigure;
import com.ppdai.das.core.configure.DataSourceConfigureChangeListener;
import com.ppdai.das.core.configure.DataSourceConfigureProvider;

import java.util.Map;
import java.util.Set;

/**
 * @description: 配置解析入口
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/06 11:37
 */
public class IctDataSourceConfigureProvider implements DataSourceConfigureProvider {

    private IctDasProperties ictDasProperties;

    public IctDataSourceConfigureProvider(IctDasProperties ictDasProperties) {
        this.ictDasProperties = ictDasProperties;
    }

    @Override
    public void initialize(Map<String, String> settings) throws Exception {

    }

    @Override
    public void setup(Set<String> dbNames) {

    }

    @Override
    public DataSourceConfigure getDataSourceConfigure(String dbName) {
        return null;
    }

    @Override
    public void register(String dbName, DataSourceConfigureChangeListener listener) {

    }

    @Override
    public void onConfigChanged(DasConfigure.DataSourceConfigureEvent event) {

    }
}