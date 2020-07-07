package com.ichangtou.controller;

import com.google.gson.Gson;
import com.ichangtou.entity.Person;
import com.ppdai.das.client.DasClient;
import com.ppdai.das.client.PageRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/07/06 11:16
 */
@RestController
@RequestMapping("query")
@Slf4j
public class QuerySampleController {

    @Autowired
    DasClient dasClient;

    @RequestMapping("count")
    @ResponseBody
    public long count() throws SQLException {
        Person person = new Person();
        person.setName("1234");
        long i = dasClient.countBySample(person);
        return i;
    }

    @RequestMapping("query")
    @ResponseBody
    public List<Person> query() throws SQLException {
        Person person = new Person();
        person.setName("1234");
        List<Person> query = dasClient.queryBySample(person);
        return query;
    }

    @RequestMapping("byPk")
    @ResponseBody
    public Person queryByPk() throws SQLException {
        Person person = new Person();
        person.setPeopleID(1);
        Person person1 = dasClient.queryByPk(person);
        return person1;
    }

    @RequestMapping("byPage")
    @ResponseBody
    public List<Person> queryByPage() throws SQLException {
        Person person = new Person();
        person.setPeopleID(1);
        List<Person> list = dasClient.queryBySample(person, PageRange.atPage(1, 5));
        return list;
    }

    @RequestMapping("asc")
    @ResponseBody
    public List<Person> asc() throws SQLException {
        Person person = new Person();
        person.setPeopleID(1);
        List<Person> list = dasClient.queryBySample(person, PageRange.atPage(1, 5, Person.PERSON.peopleid.asc()));
        return list;
    }
}