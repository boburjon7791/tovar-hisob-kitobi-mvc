package com.example.tovar_hisob_kitobi_mvc.base.common;

import com.example.tovar_hisob_kitobi_mvc.base.common.security.CustomUserDetails;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.enums.Lavozim;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(CustomControllerAdvice.class);

    @ModelAttribute("langs")
    public String langs(){
        List<String> languages=new ArrayList<>(){{
            add("uz");
            add("ru");
        }};
        languages.removeIf(s -> s.equals(lang()));
        return languages.getFirst();
    }

    @ModelAttribute("lang")
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

    @ModelAttribute("lavozimlar")
    public List<Lavozim> lavozimlar(){
        List<Lavozim> lavozimlar = Arrays.stream(Lavozim.values()).collect(Collectors.toList());
        try {
            Lavozim lavozim = Utils.customUserDetails().user().getLavozim();
            lavozimlar.removeIf(l -> l.equals(lavozim));
            lavozimlar.addFirst(lavozim);
        }catch (Exception ignore){}
        return lavozimlar;
    }

    @ModelAttribute("currentUser")
    public CustomUserDetails customUserDetails(){
        try {
            return Utils.customUserDetails();
        }catch (Exception e){
            log.error("Handled : ",e);
            return null;
        }
    }
}
