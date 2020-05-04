package com.mp.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycn
 */
@RestController
public class hello {


    @RequestMapping(value = "/")
    public String helloWorld ()  {
        return "运行了";
    }

}
