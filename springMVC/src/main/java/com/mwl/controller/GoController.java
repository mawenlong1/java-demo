package com.mwl.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HttpServletBean;

/**
 * @author mawenlong
 * @date 2018/11/28
 */
@Controller
public class GoController implements EnvironmentAware {
    private final Log logger = LogFactory.getLog(GoController.class);

    //处理HEAD类型的“/”请求
    @RequestMapping(value = { "/" }, method = { RequestMethod.HEAD })
    public String head() {
//        HttpServletBean
        return "index.jsp";
    }

    //处理GET类型的“/index”和“/”请求
    @RequestMapping(value = { "/index", "/" }, method = { RequestMethod.GET })
    public String index(Model model) {
        logger.info("=========processed by index==============");
        model.addAttribute("msg", "Go Go Go!!!");
        return "index.jsp";
    }

    private Environment environment = null;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
