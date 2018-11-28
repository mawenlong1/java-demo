package com.mwl.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author mawenlong
 * @date 2018/11/28
 */
@Controller
public class GoController {
    private final Log logger = LogFactory.getLog(GoController.class);

    //处理HEAD类型的“/”请求
    @RequestMapping(value = { "/" }, method = { RequestMethod.HEAD })
    public String head() {
        return "index.jsp";
    }

    //处理GET类型的“/index”和“/”请求
    @RequestMapping(value = { "/index", "/" }, method = { RequestMethod.GET })
    public String index(Model model) {
        logger.info("=========processed by index==============");
        model.addAttribute("msg", "Go Go Go!!!");
        return "index.jsp";
    }
}
