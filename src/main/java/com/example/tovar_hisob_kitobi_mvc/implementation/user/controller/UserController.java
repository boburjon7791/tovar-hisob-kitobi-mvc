package com.example.tovar_hisob_kitobi_mvc.implementation.user.controller;

import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.common.security.CustomUserDetails;
import com.example.tovar_hisob_kitobi_mvc.base.controller.BaseControllerMVC;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.ChangePasswordRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.filtering.UserFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(UserController._apiPrefix)
@RequiredArgsConstructor
public class UserController extends BaseControllerMVC<User, Long, UserRequestDTO, UserResponseDTO, UserFiltering> {
    public static final String _apiPrefix="/user";

    private final UserService userService;

    @Override
    public String apiPrefix() {
        return _apiPrefix;
    }

    @Override
    public Long getId(UserResponseDTO userResponseDTO) {
        return userResponseDTO.id();
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute @Valid ChangePasswordRequestDTO requestDTO, Model model){
        userService.changePassword(requestDTO);
        model.addAttribute(BaseControllerMVC._response, getBaseService().findById(Utils.customUserDetails().user().getId()));
        return apiPrefix()+"/about";
    }

    @GetMapping("/profile")
    public String myAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){
        model.addAttribute(_response, userService.getBaseMapper().toDto(customUserDetails.user()));
        return apiPrefix()+"/about";
    }
}
