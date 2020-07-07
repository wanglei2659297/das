import com.google.gson.Gson;
import com.ichangtou.entity.Person;
import com.ppdai.das.client.BatchCallBuilder;
import com.ppdai.das.client.CallBuilder;
import com.ppdai.das.client.DasClient;
import com.ppdai.das.client.DasClientFactory;
import com.ppdai.das.client.Hints;
import com.ppdai.das.client.PageRange;
import com.ppdai.das.client.Parameter;
import com.ppdai.das.client.SqlBuilder;

import java.sql.JDBCType;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: https://github.com/ppdaicorp/das/wiki/DasClient-Hints%E4%BD%BF%E7%94%A8%E6%89%8B%E5%86%8C
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/06/29 11:11
 */
public class Test {
    public static void main(String[] args) throws SQLException {
//        DefaultClientConfigLoader
//        AdvancedModStrategy
        DasClient dasClient = DasClientFactory.getClient("das_shardtest01");

//        插入数据
//        testInsert(dasClient);

        //一般查询+分页查询
        testQuery(dasClient);

        //sqlBuilder方式查询
//        testQueryBySqlBuilder(dasClient);

        //统计查询
//        testCount(dasClient);

        //触发jvm shutdown hook,关闭das的连接工厂
        System.exit(0);
    }

    /**
     * 统计测试(查询各个分库分表，累加)
     */
    private static void testCount(DasClient dasClient) throws SQLException {
        Person person = new Person();
        person.setName("1234");
        System.out.println("count:" + new Gson().toJson(dasClient.countBySample(person)));
    }

    /**
     * 测试SqlBuilder方式的查询
     */
    private static void testQueryBySqlBuilder(DasClient dasClient) throws SQLException {
        //单条结果查询(返回多条结果会报错)
//        SqlBuilder sqlBuilder = SqlBuilder.selectAllFrom(Person.PERSON)
//                .where().allOf(Person.PERSON.peopleid.eq(629184316))
//                .into(Person.class);
//        Person person = dasClient.queryObject(sqlBuilder);
//        System.out.println(new Gson().toJson(person));

        //单条结果查询，但返回多条数据(抛出异常)
//        SqlBuilder sqlBuilder = SqlBuilder.selectAllFrom(Person.PERSON)
//                .where().allOf(Person.PERSON.name.eq("1234"))
//                .into(Person.class);
//        Person person = dasClient.queryObject(sqlBuilder);
//        System.out.println(new Gson().toJson(person));

//        //循环查询所有的分库分表，然后合并数据
//        SqlBuilder sqlBuilder = SqlBuilder.selectAllFrom(Person.PERSON)
//                .where().allOf(Person.PERSON.name.eq("1234"))
//                .into(Person.class);
//        List<Person> person = dasClient.query(sqlBuilder);
//        System.out.println(new Gson().toJson(person));

        //原生sql查询(循环查询所有的分库中的具体的表)
        SqlBuilder sqlBuilder = new SqlBuilder()
                .appendTemplate(
                        "select * from person_0 where PeopleID = ?",
                        Parameter.integerOf("", 629184316)
                )
                .into(Person.class)
//                .limit(2,10) //分页
//                .top(10)//top 10
//                .offset(2,10)//分页
                ;
        List<Person> person = dasClient.query(sqlBuilder);
        System.out.println(new Gson().toJson(person));
    }

    /**
     * 查询测试
     */
    private static void testQuery(DasClient dasClient) throws SQLException {
        //主键查询(计算分库分表后查询指定库的指定表)
        System.out.println("pk query:" + new Gson().toJson(dasClient.queryByPk(Person.builder().peopleid(629184316).build())));

        //根据非主键字段查询(循环查询所有表)
        System.out.println("sample query:" + new Gson().toJson(dasClient.queryBySample(Person.builder().name("1234").build())));


//        分页查询(不排序)(如果分2个库，每个库4个表，共查出来80条数据)
        System.out.println("====no sort:" + new Gson().toJson(dasClient.queryBySample(
                Person.builder().name("1234").build(),
                PageRange.atPage(1, 5))));
//        分页查询(按照peopleid升序)(如果分2个库，每个库4个表，共查出来80条数据)
        System.out.println("====asc sort:" + new Gson().toJson(dasClient.queryBySample(
                Person.builder().name("1234").build(),
                PageRange.atPage(1, 5, Person.PERSON.peopleid.asc()))));
        //分页查询(按照peopleid降序)(如果分2个库，每个库4个表，共查出来80条数据)
        System.out.println("====desc sort:" + new Gson().toJson(dasClient.queryBySample(
                Person.builder().name("1234").build(),
                PageRange.atPage(1, 10, Person.PERSON.peopleid.desc()))));


    }

    private static void testInsert(DasClient dasClient) throws SQLException {
        Integer id = Integer.parseInt(new SimpleDateFormat("MMddHHmmss").format(new Date()));
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person pk = new Person();
            pk.setPeopleID(id + i);
            pk.setCityID(123);
            pk.setName("1234");

            //person手动设置主键的插入方式
            dasClient.insert(pk, Hints.hints().insertWithId());

            //自增长主键，返回主键到实体类中
//            dasClient.insert(pk, Hints.hints().setIdBack());
            System.out.println(new Gson().toJson(pk));
            list.add(pk);
        }

        //批量插入
        System.out.println(new Gson().toJson(dasClient.batchInsert(list)));
    }

    /**
     * 测试更新
     */
    private static void testUpdate(DasClient dasClient) throws SQLException {
        Integer id = Integer.parseInt(new SimpleDateFormat("MMddHHmmss").format(new Date()));
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person pk = new Person();
            pk.setPeopleID(id + i);
            pk.setCityID(123);
            pk.setName("1234");

            dasClient.update(pk);

            list.add(pk);
        }

        //批量插入,返回主键
        System.out.println(new Gson().toJson(dasClient.batchUpdate(list)));
    }

    /**
     * 测试删除
     */
    private static void testDelete(DasClient dasClient) throws SQLException {

        //主键删除
        Person pk = new Person();
        pk.setPeopleID(629184316);
        dasClient.deleteByPk(pk);


        //name删除
        Person person = new Person();
        person.setName("1234");
        dasClient.deleteBySample(person);

        //批量删除
        Integer id = Integer.parseInt(new SimpleDateFormat("MMddHHmmss").format(new Date()));
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person pk2 = new Person();
            pk2.setPeopleID(id + i);
            pk2.setCityID(123);
            pk2.setName("1234");
            list.add(pk2);
        }
        dasClient.batchDelete(list);
    }

    /**
     * 测试调用存储过程
     */
    private static void testCallProduce(DasClient dasClient) throws SQLException {

        //单次调用存储过程
        CallBuilder cb = new CallBuilder("SP_WITHOUT_OUT_PARAM");
        cb.registerInput("v_id", JDBCType.INTEGER, 7);
        cb.registerInput("v_cityID", JDBCType.INTEGER, 7);
        cb.registerInput("v_countryID", JDBCType.INTEGER, 7);
        cb.registerInput("v_name", JDBCType.VARCHAR, "666");
        dasClient.call(cb);

        //批量调用存储过程
        BatchCallBuilder batchCallBuildercb = new BatchCallBuilder("SP_WITHOUT_OUT_PARAM");
        batchCallBuildercb.registerInput("v_id", JDBCType.INTEGER);
        batchCallBuildercb.registerInput("v_cityID", JDBCType.INTEGER);
        batchCallBuildercb.registerInput("v_countryID", JDBCType.INTEGER);
        batchCallBuildercb.registerInput("v_name", JDBCType.VARCHAR);

        batchCallBuildercb.addBatch(7, 7, 7, "777");
        batchCallBuildercb.addBatch(17, 17, 17, "1777");
        batchCallBuildercb.addBatch(27, 27, 27, "2777");
        int[] ret = dasClient.batchCall(batchCallBuildercb);
    }

    /**
     * 测试执行事务
     */
    private static void testTransaction(DasClient dasClient) throws SQLException {

        //无返回值
        dasClient.execute(() -> {
            testDelete(dasClient);
        });

        //有返回值
        Person execute = dasClient.execute(() -> {
            return dasClient.queryByPk(Person.builder().peopleid(629184316).build());
        });
    }
}