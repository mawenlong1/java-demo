package com.mwl.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/book")
@SessionAttributes(value = { "book", "description" }, types = Double.class)
public class BookController {
    private final Log logger = LogFactory.getLog(BookController.class);

    @RequestMapping("index")
    public String index(Model model) throws Exception {
        model.addAttribute("book", "金刚经");
        model.addAttribute("description", "般若系列");
        model.addAttribute("price", new Double(99.88));
        return "redirect:get";
    }

    @RequestMapping("get")
    public String getBySessionAttributes(@ModelAttribute("book") String book, ModelMap modelMap,
                                         SessionStatus sessionStatus) {
        logger.info("============getBySessionAttributes()=======================");
        logger.info("get by @ModelAttribute book");
        logger.info("book:" + modelMap.get("book") + "\t des:" + modelMap.get("description") + "\t price:" +
                    modelMap.get("price"));
        //通知完成以后获取不到
        sessionStatus.setComplete();
        return "redirect:complete";
    }

    @RequestMapping("complete")
    public String afterComplete(ModelMap modelMap) {
        logger.info("============afterComplete()=======================");
        logger.info("book:" + modelMap.get("book") + "\t des:" + modelMap.get("description") + "\t price:" +
                    modelMap.get("price"));
        return "index";
    }

    //取出uri模板中的变量作为参数
    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public String getProduct(@PathVariable("productId") String productId) {
        System.out.println("Product Id : " + productId);
        return "hello";
    }

    @GetMapping("/{name:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{ext:\\.[a-z]+}")
    public void handle(@PathVariable String version, @PathVariable String ext) {
        // ...
    }
    @GetMapping(path = "/pets/{petId}", params = "myParam=myValue")
    public void findPet(@PathVariable String petId) {
        // ...
    }
    @GetMapping("/demo")
    public void handle(
            @RequestHeader("Accept-Encoding") String encoding,
            @RequestHeader("Keep-Alive") long keepAlive) {
        //...
    }

    @RequestMapping("/{bookId}")
    @ResponseBody
    public String getBookId(@PathVariable("bookId") String bookId) {
        String msg = "bookId:" + bookId;
        logger.info("============getBySessionAttributes()=======================");
        logger.info("bookId:" + bookId);
        return msg;
    }

    @RequestMapping(value = "/tel/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String getTel(@PathVariable("name") String name, @MatrixVariable(required = false) String gender,
                         @MatrixVariable(required = false) String group) {
        String msg = "bookId:" + name + "\ngender:" + gender + "\ngroup" + group;
        logger.info("============getBySessionAttributes()=======================");
        logger.info("bookId:" + name);
        return msg;
    }
}
