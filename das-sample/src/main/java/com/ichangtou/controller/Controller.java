package com.ichangtou.controller;

import com.google.gson.Gson;
import com.ichangtou.entity.Person;
import com.ppdai.das.client.DasClient;
import com.ppdai.das.client.Hints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/06 11:16
 */
@RestController
@RequestMapping("test")
@Slf4j
public class Controller {

    @Autowired
    DasClient dasClient;

    @RequestMapping("test")
    @ResponseBody
    public long test() throws SQLException {

        log.info("执行 test DasClient");
        Person person = new Person();
        person.setName("1234");
        long i = dasClient.countBySample(person);
        System.out.println("count:" + new Gson().toJson(i));

        return i;
    }

    @RequestMapping("testInsert")
    @ResponseBody
    public int[] testInsert() throws SQLException {
        return testInsert(dasClient);
    }

    private int[] testInsert(DasClient dasClient) throws SQLException {
        Integer id = Integer.parseInt(new SimpleDateFormat("HHmmssSSS").format(new Date()));
        List<Person> list = new ArrayList<>();
        for (int i = 100; i < 130; i++) {
            Person pk = new Person();
            pk.setPeopleID(id + i);
            pk.setCityID(123);
            pk.setName("1234");

            //person手动设置主键的插入方式
//            dasClient.insert(pk, Hints.hints().insertWithId());

            //自增长主键，返回主键到实体类中
//            dasClient.insert(pk, Hints.hints().setIdBack());
            System.out.println(new Gson().toJson(pk));
            list.add(pk);
        }

        int[] arr = dasClient.batchInsert(list);
        //批量插入
        System.out.println(new Gson().toJson(arr));

        return arr;
    }
}