package com.mwl.controller;

import com.mwl.engine.Engine;
import com.mwl.engine.ExtensionEngine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mawenlong
 * @date 2019/02/12
 */
@RestController
@RequestMapping("/")
public class MainController {
    private Engine engine = new ExtensionEngine();

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        System.out.println("hello world !");
        return "hello world ! ";
    }

    @RequestMapping(value = "engine", method = RequestMethod.GET)
    public String engine(@RequestParam(name = "bizCode") String bizCode,
                         @RequestParam(name = "params", required = false) String params) {
        System.out.println("extension");
        return engine.process(bizCode, params);
    }
}
