package com.example.tovar_hisob_kitobi_mvc.base.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomControllerAdvice {
    @ModelAttribute("langs")
    public String langs(){
        List<String> languages=new ArrayList<>(){{
            add("uz");
            add("ru");
        }};
        languages.removeIf(s -> s.equals(lang()));
        return languages.getFirst();
    }

    public String lang(){
        return LocaleContextHolder.getLocale().getLanguage();
    }

    @ModelAttribute("path")
    public String path(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        if (requestURI.contains("language")) {
            return "https://online.pdp.uz";
        }
        return requestURI;
    }
}
