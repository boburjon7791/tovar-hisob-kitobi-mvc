package com.example.tovar_hisob_kitobi_mvc.implementation.user.controller;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.common.security.CustomUserDetails;
import com.example.tovar_hisob_kitobi_mvc.base.controller.BaseControllerMVC;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.ChangePasswordRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.enums.Lavozim;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.filtering.UserFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(UserController._apiPrefix)
@RequiredArgsConstructor
public class UserController extends BaseControllerMVC<User, Long, UserRequestDTO, UserResponseDTO, UserFiltering> {
    public static final String _apiPrefix="user";

    private final UserService userService;

    @Override
    public String apiPrefix() {
        return _apiPrefix;
    }

    @Override
    public Long getId(UserResponseDTO userResponseDTO) {
        return userResponseDTO.id();
    }

    @GetMapping("/change-password/{id}")
    public String changePassword(Model model, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Utils.hasRole(id, Lavozim.DIRECTOR);
        model.addAttribute("id",id);
        return "auth/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute @Valid ChangePasswordRequestDTO requestDTO, Model model){
        userService.changePassword(requestDTO);
        model.addAttribute(BaseControllerMVC._response, getBaseService().findById(Utils.customUserDetails().user().getId()));
        return apiPrefix()+"/about";
    }

    @GetMapping("/profile")
    public String myAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){
        UserResponseDTO responseDTO = userService.getBaseMapper().toDto(customUserDetails.user());
        model.addAttribute(_response, ApiResponse.ok(responseDTO));
        return apiPrefix()+"/about";
    }

    @PostMapping("/change-role")
    public String changeRole(@RequestParam Lavozim lavozim, @RequestParam Long id){
        userService.changeRole(id, lavozim);
        return redirectList();
    }

    @Override
    @GetMapping("/find/{id}")
    public String findById(Long id, Model model) {
        Utils.hasRole(id, Lavozim.DIRECTOR);
        return super.findById(id, model);
    }

    @Override
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    public String findAll(UserFiltering request, Model model) {
        return super.findAll(request, model);
    }

    @Override
    public String deleteById(Long id) {
        throw new RuntimeException();
    }
}
