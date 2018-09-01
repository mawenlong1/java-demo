package com.mwl.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mawenlong
 * @date 2018/08/26
 * describe:
 */
@RestController
public class HelloWorldController {
    @RequestMapping("/info")
    public String info(){
        return "HelloWorld";
    }

}
