package com.taotao.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/{page}")
    public String returnPage(@PathVariable String page){
        return page;
    }

    @RequestMapping("item-add")
    public String result(){
        return "item-add";
    }

    @RequestMapping("/item-edit")
    public String edit(){
        return "item-edit";
    }

    @RequestMapping("/item-param-edit")
    public String toItemParamEdit() {
        return "item-param-edit";
    }
}
