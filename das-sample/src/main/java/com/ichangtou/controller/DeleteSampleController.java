package com.ichangtou.controller;

import com.google.gson.Gson;
import com.ichangtou.entity.Person;
import com.ppdai.das.client.DasClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/06 11:16
 */
@RestController
@RequestMapping("delete")
@Slf4j
public class DeleteSampleController {

    @Autowired
    DasClient dasClient;

    @RequestMapping("sample")
    @ResponseBody
    public long sample() throws SQLException {
        Person person = new Person();
        person.setName("1234");
        long i = dasClient.countBySample(person);
        return i;
    }

    @RequestMapping("batch")
    @ResponseBody
    public int[] batchDelete() throws SQLException {

        List<Person> list = new ArrayList<>();
        list.add(Person.builder().peopleid(1).build());
        list.add(Person.builder().peopleid(2).build());

        return dasClient.batchDelete(list);
    }
}