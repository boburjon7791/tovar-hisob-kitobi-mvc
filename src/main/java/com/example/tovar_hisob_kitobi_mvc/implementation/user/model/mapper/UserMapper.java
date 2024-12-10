package com.example.tovar_hisob_kitobi_mvc.implementation.user.model.mapper;

import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.model.mapper.BaseMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements BaseMapper<User, UserRequestDTO, UserResponseDTO> {
    @Override
    public User toEntity(UserRequestDTO requestDTO) {
        return new User(requestDTO.ism(), requestDTO.familya(), requestDTO.lavozim(), requestDTO.login(), requestDTO.parol(), requestDTO.telefonRaqam());
    }

    @Override
    public UserResponseDTO toDto(User user) {
        return new UserResponseDTO(user.getId(), Utils.formatDate(user.getCreatedAt()), Utils.formatDate(user.getUpdatedAt()), user.getCreatedBy(), user.getUpdatedBy(), null, null, user.getIsm(), user.getFamilya(), user.getLogin(), user.getTelefonRaqam(), user.getLavozim(),null);
    }

    @Override
    public void update(User user, UserRequestDTO requestDTO){
        user.setLogin(requestDTO.login());
        user.setIsm(requestDTO.ism());
        user.setFamilya(requestDTO.familya());
        user.setTelefonRaqam(requestDTO.telefonRaqam());
    }
}
