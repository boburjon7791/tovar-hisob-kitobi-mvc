package com.example.tovar_hisob_kitobi_mvc.implementation.user.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.ChangePasswordRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.filtering.UserFiltering;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, Long, UserRequestDTO, UserResponseDTO, UserFiltering> {
    private final PasswordEncoder passwordEncoder;
    @Override
    public void checkCreating(UserRequestDTO userRequestDTO) {

    }

    @Override
    public void checkUpdating(UserRequestDTO userRequestDTO, Long aLong) {

    }

    public void changePassword(ChangePasswordRequestDTO requestDTO){
        User user = Utils.customUserDetails().user();
        if (!requestDTO.newPswd().equals(requestDTO.confirmPswd())) {
            throw new ApiException(getLocalization().getMessage("not_confirmed_pswd"));
        }
        if (!passwordEncoder.matches(requestDTO.oldPswd(), user.getParol())) {
            throw new ApiException(getLocalization().getMessage("incorrect_pswd"));
        }
        user.setParol(passwordEncoder.encode(requestDTO.newPswd()));
        getBaseRepository().save(user);
    }
}
