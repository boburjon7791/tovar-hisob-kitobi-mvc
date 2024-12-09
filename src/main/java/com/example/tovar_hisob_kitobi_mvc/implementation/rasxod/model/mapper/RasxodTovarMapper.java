package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.mapper;

import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.model.mapper.BaseMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class RasxodTovarMapper implements BaseMapper<RasxodTovar, RasxodTovarRequestDTO, RasxodTovarResponseDTO> {
    private final UserService userService;
    @Override
    public RasxodTovar toEntity(RasxodTovarRequestDTO requestDTO) {
        return new RasxodTovar();
    }

    public RasxodTovarResponseDTO toDto(RasxodTovar rasxodTovar, List<RasxodTovarDetailResponseDTO> rasxodTovarDetails) {
        User created = userService.entity(rasxodTovar.getCreatedBy());
        User updated = userService.entity(rasxodTovar.getUpdatedBy());
        return new RasxodTovarResponseDTO(rasxodTovar.getId(), Utils.formatDate(rasxodTovar.getCreatedAt()), Utils.formatDate(rasxodTovar.getUpdatedAt()), created.getId(), updated.getId(), created.getFamilya()+" "+created.getIsm(), updated.getFamilya()+" "+updated.getId(), rasxodTovar.getTotalSumma(), rasxodTovarDetails, rasxodTovar.isTasdiqlandi());
    }

    @Override
    public RasxodTovarResponseDTO toDto(RasxodTovar rasxodTovar) {
        User created = userService.entity(rasxodTovar.getCreatedBy());
        User updated = userService.entity(rasxodTovar.getUpdatedBy());
        return new RasxodTovarResponseDTO(rasxodTovar.getId(), Utils.formatDate(rasxodTovar.getCreatedAt()), Utils.formatDate(rasxodTovar.getUpdatedAt()), created.getId(), updated.getId(), created.getFamilya()+" "+created.getIsm(), updated.getFamilya()+" "+updated.getIsm(), rasxodTovar.getTotalSumma(), null, rasxodTovar.isTasdiqlandi());
    }

    @Override
    public void update(RasxodTovar rasxodTovar, RasxodTovarRequestDTO rasxodTovarRequestDTO){
        rasxodTovar.setIzoh(rasxodTovarRequestDTO.izoh());
    }
}
