package com.ichangtou;

import com.ppdai.das.core.configure.DataSourceConfigure;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.sql.DataSource;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/01 17:42
 */
public interface IctDataSource {

    DataSource createDatasource(String name, DataSourceConfigure dataSourceConfigure) throws InstanceNotFoundException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException;

    int getActive();

    Object getPool();

    int getIdle();

    void purge();

    void close(boolean flag);

    void close();
}