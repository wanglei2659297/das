package com.ppdai.das.client;

import com.ppdai.das.client.delegate.DasDelegate;
import com.ppdai.das.client.delegate.datasync.DataSyncDasDelegate;
import com.ppdai.das.client.delegate.local.ClientDasDelegate;
import com.ppdai.das.client.delegate.remote.DasRemoteDelegate;
import com.ppdai.das.core.ClientConfigureLoader;
import com.ppdai.das.core.DasConfigure;
import com.ppdai.das.core.DasConfigureContext;
import com.ppdai.das.core.DasConfigureFactory;
import com.ppdai.das.core.DasServerInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class DasClientFactory {
    private static final Logger logger = LoggerFactory.getLogger(DasClientVersion.getLoggerName());

    private static final AtomicReference<String> customerClientVersionRef = new AtomicReference<>();
    private static final AtomicReference<String> appIdRef = new AtomicReference<>();

    private static final AtomicBoolean proxyModeRef = new AtomicBoolean(false);
    private static ClientConfigureLoader clientConfigureLoader;

    public static void setClientConfigureLoader(ClientConfigureLoader clientConfigureLoader) {
        DasClientFactory.clientConfigureLoader = clientConfigureLoader;
    }

    public static DasClient getClient(String logicDbName) throws SQLException {
        initClientFactory();
        return new DasClient(create(appIdRef.get(), logicDbName, customerClientVersionRef.get()));
    }

    private static DasDelegate create(String appId, String logicDbName, String customerClientVersion) {
        try {
            if (proxyModeRef.get()) {
                List<DasServerInstance> servers = clientConfigureLoader.getServerInstances();
                return new DasRemoteDelegate(appId, logicDbName, customerClientVersion, servers, clientConfigureLoader.getDasLogger());
            } else {
                ClientDasDelegate dasDelegate = new ClientDasDelegate(appId, logicDbName, customerClientVersion);
                return new DataSyncDasDelegate(dasDelegate);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String getAppId() {
        return appIdRef.get();
    }

    public static void initClientFactory() {
        logger.info("Start initialize DAS client");
        try {
//                ClientConfigureLoader loader = ServiceLoaderHelper.getInstance(ClientConfigureLoader.class, DefaultClientConfigLoader.class);
//                if(loader == null) {
//                    throw new IllegalStateException("Can not find ClientConfigureLoader");
//                }

            customerClientVersionRef.set(clientConfigureLoader.getCustomerDasClientVersion());
            proxyModeRef.set(clientConfigureLoader.isProxyEnabled());
            appIdRef.set(clientConfigureLoader.getAppId());

            logger.info("DAS client connection mode: " + (clientConfigureLoader.isProxyEnabled() ? "proxy" : "direct"));

            DasConfigureContext configContext;
            if (clientConfigureLoader.isProxyEnabled()) {
                configContext = new DasConfigureContext(clientConfigureLoader.getDasLogger());
            } else {
                Map<String, DasConfigure> configureMap = new HashMap<>();
                DasConfigure config = clientConfigureLoader.load();
                configureMap.put(clientConfigureLoader.getAppId(), config);
                configContext = new DasConfigureContext(configureMap, config.getDasLogger(), config.getTaskFacory(), config.getConnectionLocator(), config.getDatabaseSelector());
                logger.info("Successfully load local DAS confiure");
            }

            DasConfigureFactory.initialize(configContext);
            logger.info("DAS client successfully initialzed");
        } catch (Throwable e) {
            logger.info("DAS client initilization failed", e);
            throw new IllegalStateException("Das client initilization failed", e);
        }
    }
}
