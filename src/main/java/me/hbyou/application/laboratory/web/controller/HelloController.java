package me.hbyou.application.laboratory.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tony(YouHaibo)
 * @date 2017-08-05 15:04
 */
@RestController
public class HelloController {

    @RequestMapping("/hello.htm")
    public String hello() {
        return "Hello World.";
    }

}
