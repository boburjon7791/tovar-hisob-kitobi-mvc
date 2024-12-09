package com.example.tovar_hisob_kitobi_mvc.base.common.internationalization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/language")
public class LocalizationChanger {
    @Value("${link}")
    private String link;

    @GetMapping
    public String changeLanguage(@RequestParam String path){
        return "redirect:"+path;
    }
}
