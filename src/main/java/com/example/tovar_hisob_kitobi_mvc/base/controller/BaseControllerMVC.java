package com.example.tovar_hisob_kitobi_mvc.base.controller;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
public abstract class BaseControllerMVC<ENTITY, ID, REQUEST_DTO, RESPONSE_DTO, FILTERING> {
    public static final String _response="response";
    private BaseService<ENTITY,ID, REQUEST_DTO, RESPONSE_DTO, FILTERING> baseService;
    private UserService userService;

    @Autowired
    public void setUserService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBaseService(@Lazy BaseService<ENTITY, ID, REQUEST_DTO, RESPONSE_DTO, FILTERING> baseService) {
        this.baseService = baseService;
    }

    public abstract String apiPrefix();
    public abstract ID getId(RESPONSE_DTO responseDto);

    public String redirectList(){
        return "redirect:/"+apiPrefix()+"/list";
    }

    public String redirectForm(ID id){
        return "redirect:/"+apiPrefix()+"/find/"+id;
    }

    @GetMapping("/create")
    public String create(){
        return apiPrefix()+"/form";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute REQUEST_DTO dto){
        ApiResponse<RESPONSE_DTO> response = baseService.create(dto);
        return redirectForm(getId(response.getData()));
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute REQUEST_DTO dto, @PathVariable ID id){
        baseService.update(dto, id);
        return redirectForm(id);
    }

    @GetMapping("/find/{id}")
    public String findById(@PathVariable ID id, Model model){
        ApiResponse<RESPONSE_DTO> response = baseService.findById(id);
        model.addAttribute(_response, response);
        return apiPrefix()+"/about";
    }

    @GetMapping("/list")
    public String findAll(@ModelAttribute FILTERING request, Model model){
        ApiResponse<List<RESPONSE_DTO>> response = baseService.findAll(request);
        model.addAttribute(_response,response);
        model.addAttribute("request", request);
        String userId = Utils.request().getParameter("userId");
        if (userId!=null && !userId.isBlank()) {
            model.addAttribute("user", userService.findById(Long.valueOf(userId)));
        }
        return apiPrefix()+"/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable ID id){
        baseService.deleteById(id);
        return redirectList();
    }

    public void addUsers(Model model, UserService userService){
        String userId = Utils.request().getParameter("userId");
        List<UserResponseDTO> users = userService.findAll();
        users.addFirst(UserResponseDTO.builder().ism("").familya("").build());
        if (userId!=null && !userId.isBlank()) {
            Long id=Long.valueOf(userId);
            UserResponseDTO user = users.stream()
                    .filter(userResponseDTO -> userResponseDTO.id()!=null && userResponseDTO.id().equals(id))
                    .findFirst()
                    .orElseThrow();
            users.removeIf(userResponseDTO -> userResponseDTO.id()!=null && userResponseDTO.id().equals(id));
            users.addFirst(user);
        }
        model.addAttribute("users", users);
    }
}
