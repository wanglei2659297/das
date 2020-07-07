package com.ppdai.das.client;

import com.ppdai.das.client.transaction.normal.BaseDalTransactionalAnnotationTest;
import com.ppdai.das.core.test.DasRunnerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ppdai.das.client.delegate.datasync.DataSyncConfigurationTest;
import com.ppdai.das.client.delegate.datasync.SequenceGeneratorTest;
import com.ppdai.das.client.sqlbuilder.AllSqlBuilderTests;
import com.ppdai.das.client.sqlbuilder.SqlBuilderSerializeTest;
import com.ppdai.das.configure.SlaveFreshnessScannerMysqlTest;
import com.ppdai.das.core.datasource.RefreshableDataSourceTest;
import com.ppdai.das.core.helper.ShardingManagerTest;
import com.ppdai.das.strategy.AllStrategyTests;
import com.ppdai.das.util.ConvertUtilsTest;

@RunWith(Suite.class)
@SuiteClasses({
    AllSqlBuilderTests.class,
    AllStrategyTests.class,
    AllTableDaoTests.class,

    BatchCallBuilderTest.class,
    CallBuilderTest.class,

    DasClientTest.class,
    DasClientDBTest.class,
    DasClientTableTest.class,
    DasClientDbTableTest.class,
    DasClientDbTableZeroTest.class,
    DasClientDiagnoseTest.class,

    SqlBuilderDBShardTest.class,
    SqlBuilderTableShardTest.class,
    SqlBuilderDbTableShardTest.class,

    DasRunnerTest.class,
    DistributedTransactionDbTest.class,
    DistributedTransactionTableTest.class,
    NestedTransactionTest.class,
    BaseDalTransactionalAnnotationTest.class,

    ConvertUtilsTest.class,
    DataSyncConfigurationTest.class,
    SequenceGeneratorTest.class,
    ShardingManagerTest.class,
    RefreshableDataSourceTest.class,
    SqlBuilderSerializeTest.class,
    SlaveFreshnessScannerMysqlTest.class,
})
public class AllDasClientTests {
}
