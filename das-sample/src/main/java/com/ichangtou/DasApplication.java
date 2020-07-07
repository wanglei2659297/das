package com.ichangtou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/06/29 18:53
 */
@SpringBootApplication
@ComponentScan(value = {"com.ppdai.das","com.ichangtou"})
public class DasApplication {

    public static void main(String[] args) {
        SpringApplication.run(DasApplication.class, args);
    }

}