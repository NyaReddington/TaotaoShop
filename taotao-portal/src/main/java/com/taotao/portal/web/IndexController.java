package com.taotao.portal.web;

import com.taotao.portal.service.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private AdsService adsService;

    @RequestMapping("/")
    public String toIndex(Model model) {
        // 同步加载，获取到首页大广告数据
        String adResult = adsService.getAdItemList();
        model.addAttribute("ad1", adResult);
        return "index";
    }

    /*@RequestMapping("/")
    public String toIndex() {
        return "index";
    }

    @RequestMapping("/content/ads")
    @ResponseBody
    public String getAds() {
        String adResult = adsService.getAdItemList();
        return adResult;
    }*/

}
