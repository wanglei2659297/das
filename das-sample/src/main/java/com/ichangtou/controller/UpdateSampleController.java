package com.ichangtou.controller;

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
@RequestMapping("update")
@Slf4j
public class UpdateSampleController {

    @Autowired
    DasClient dasClient;

    @RequestMapping("sample")
    @ResponseBody
    public long update() throws SQLException {
        Person person = new Person();
        person.setPeopleID(1);
        person.setName("123467");
        return dasClient.update(person);
    }

    @RequestMapping("batch")
    @ResponseBody
    public int[] batchUpdate() throws SQLException {
        List<Person> list = new ArrayList<>();
        list.add(Person.builder().peopleid(1).name("!2发多少3456").build());
        list.add(Person.builder().peopleid(2).name("!23456").build());
        return dasClient.batchUpdate(list);
    }
}