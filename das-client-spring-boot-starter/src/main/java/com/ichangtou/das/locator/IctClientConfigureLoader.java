package com.ichangtou.das.locator;

import com.ichangtou.das.autoconfigure.configure.IctDasConfigureFactory;
import com.ichangtou.das.properties.IctDasProperties;
import com.ppdai.das.core.ClientConfigureLoader;
import com.ppdai.das.core.ConnectionLocator;
import com.ppdai.das.core.DasConfigure;
import com.ppdai.das.core.DasLogger;
import com.ppdai.das.core.DasServerInstance;
import com.ppdai.das.core.DatabaseSelector;
import com.ppdai.das.core.DatabaseSet;
import com.ppdai.das.core.task.TaskFactory;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/01 13:51
 */
@Setter
@Getter
public class IctClientConfigureLoader implements ClientConfigureLoader {

    private static DasConfigure ictDasConfigure;
    Map<String, DatabaseSet> databaseSets = new HashMap<>();

    public IctClientConfigureLoader(ConnectionLocator connectionLocator,
                                    DatabaseSelector databaseSelector,
                                    TaskFactory taskFactory,
                                    DasLogger dasLogger,
                                    IctDasProperties ictDasProperties) throws Exception {
        ictDasConfigure = this.init(connectionLocator, databaseSelector, taskFactory, dasLogger, ictDasProperties);
    }

    /**
     * 初始化
     */
    private DasConfigure init(
            ConnectionLocator connectionLocator,
            DatabaseSelector databaseSelector,
            TaskFactory taskFactory,
            DasLogger dasLogger,
            IctDasProperties ictDasProperties) throws Exception {
        Assert.isTrue(StringUtils.isNotBlank(ictDasProperties.getAppId())," das appId can't be blank");
        return
                new DasConfigure(ictDasProperties.getAppId(),
                        IctDasConfigureFactory.convertDatabaseSet(ictDasProperties),
                        dasLogger,
                        getConnectionLocator(connectionLocator, ictDasProperties),
                        getTaskFactory(taskFactory, ictDasProperties),
                        getDatabaseSelector(databaseSelector, ictDasProperties)
                );
    }

    private DasLogger getDasLoggery(DasLogger dasLogger, DasConfigure dasConfigure) {
        return dasLogger != null ? dasLogger : dasConfigure.getDasLogger();
    }

    private TaskFactory getTaskFactory(TaskFactory taskFactory, IctDasProperties ictDasProperties) throws Exception {
        taskFactory.initialize(ictDasProperties.getSettings());
        return taskFactory;
    }

    private DatabaseSelector getDatabaseSelector(DatabaseSelector databaseSelector, IctDasProperties ictDasProperties) throws Exception {
        databaseSelector.initialize(ictDasProperties.getSettings());
        return databaseSelector;
    }

    private ConnectionLocator getConnectionLocator(ConnectionLocator connectionLocator, IctDasProperties ictDasProperties) throws Exception {
        connectionLocator.initialize(ictDasProperties.getSettings());
        return connectionLocator;
    }

    private String getAppId(String appId, DasConfigure dasConfigure) {
        return StringUtils.isNotBlank(appId) ? appId : dasConfigure.getAppId();
    }

    @Override
    public String getAppId() {
        return IctClientConfigureLoader.ictDasConfigure.getAppId();
    }

    @Override
    public String getCustomerDasClientVersion() {
        return "IctClientConfigureLoader";
    }

    @Override
    public boolean isProxyEnabled() throws Exception {
        return false;
    }

    @Override
    public DasConfigure load() throws Exception {
        return IctClientConfigureLoader.ictDasConfigure;
    }

    @Override
    public DasLogger getDasLogger() throws Exception {
        return IctClientConfigureLoader.ictDasConfigure.getDasLogger();
    }

    @Override
    public List<DasServerInstance> getServerInstances() throws Exception {
        return Collections.emptyList();
    }
}