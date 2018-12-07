package com.mwl.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author mawenlong
 * @date 2018/12/07
 */
@Controller
@SessionAttributes("articleId")
public class FollowMeController {
    private final Log logger = LogFactory.getLog(FollowMeController.class);
    private String[] sensitiveWords = new String[] { "k1", "k2" };

    @ModelAttribute("comment")
    public String replaceSensitiveWords(String comment) {
        if (comment != null) {
            logger.info("原始comment：" + comment);
            for (String sw : sensitiveWords) {
                comment = comment.replaceAll(sw, "");
            }
            logger.info("去掉敏感词后的comment：" + comment);
        }
        return comment;
    }

    @RequestMapping(value = "/articles/{articleId}/comment")
    public String doComment(@PathVariable String articleId, RedirectAttributes attributes, Model model) {
        attributes.addFlashAttribute("comment", model.asMap().get("comment"));
        model.addAttribute("articleId", articleId);
//        保存到数据库
        return "redirect:/showArticle";
    }
    @RequestMapping(value = "/showArticle",method = RequestMethod.GET)
    public String showArticle(Model model, SessionStatus sessionStatus) {
        String articleId = (String) model.asMap().get("articleId");
        model.addAttribute("articleTitle", articleId + "号标题");
        model.addAttribute("article", articleId + "文章内容");
        sessionStatus.setComplete();
        return "article";
    }
}
