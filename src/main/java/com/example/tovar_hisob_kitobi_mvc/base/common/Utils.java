package com.example.tovar_hisob_kitobi_mvc.base.common;

import com.example.tovar_hisob_kitobi_mvc.base.common.security.CustomUserDetails;
import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntity;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.enums.Lavozim;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Utils {
    public static CustomUserDetails customUserDetails(){
        try {
            return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return null;
        }
    }
    public static HttpServletRequest request(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    public static HttpServletResponse response(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
    public static String formatDate(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
    public static Sort sortByCreatedAtDesc(){
        return Sort.by(Sort.Direction.DESC, BaseEntity._createdAt);
    }

    public static void hasRole(Long id, Lavozim...lavozim) {
        User user = customUserDetails().user();
        if (!user.getId().equals(id) && Arrays.stream(lavozim).noneMatch(l -> user.getLavozim().equals(l))) {
            throw new AccessDeniedException("");
        }
    }
}
