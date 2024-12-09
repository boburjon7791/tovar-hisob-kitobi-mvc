package com.example.tovar_hisob_kitobi_mvc.base.exception;

import com.example.tovar_hisob_kitobi_mvc.base.common.CustomControllerAdvice;
import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.common.ValidationErrorDTO;
import com.example.tovar_hisob_kitobi_mvc.base.controller.BaseControllerMVC;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final CustomControllerAdvice customControllerAdvice;
    @ExceptionHandler(Exception.class)
    public String handler(Exception e, Model model){
        log.error("Handle : {0}",e);
        model.addAttribute(BaseControllerMVC._response, e.getMessage());
        addCustom(model);
        return "/errors/server-error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handler(MethodArgumentNotValidException e, Model model){
        log.error("Handle : {0}",e);
        List<ValidationErrorDTO> errorDTOList = e.getFieldErrors().stream().map(fieldError -> new ValidationErrorDTO(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        model.addAttribute(BaseControllerMVC._response, errorDTOList);
        addCustom(model);
        return "/errors/validation-error";
    }

    @ExceptionHandler(ApiException.class)
    public String handler(ApiException e, Model model){
        log.warn("Handle : {0}",e);
        model.addAttribute(BaseControllerMVC._response, e.getMessage());
        addCustom(model);
        return "/errors/bad-request";
    }
    public void addCustom(Model model){
        model.addAttribute("lang",customControllerAdvice.lang());
        model.addAttribute("langs",customControllerAdvice.langs());
        model.addAttribute("path",customControllerAdvice.path(Utils.request()));
    }
}
