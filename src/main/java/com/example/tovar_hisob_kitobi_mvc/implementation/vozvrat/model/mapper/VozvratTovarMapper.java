package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.mapper;

import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.model.mapper.BaseMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.service.UserService;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class VozvratTovarMapper implements BaseMapper<VozvratTovar, VozvratTovarRequestDTO, VozvratTovarResponseDTO> {
    private final UserService userService;
    @Override
    public VozvratTovar toEntity(VozvratTovarRequestDTO requestDTO) {
        return new VozvratTovar();
    }

    @Override
    public VozvratTovarResponseDTO toDto(VozvratTovar vozvratTovar) {
        User created = userService.entity(vozvratTovar.getCreatedBy());
        User updated = userService.entity(vozvratTovar.getUpdatedBy());
        return new VozvratTovarResponseDTO(vozvratTovar.getId(), Utils.formatDate(vozvratTovar.getCreatedAt()), Utils.formatDate(vozvratTovar.getUpdatedAt()), created.getId(), updated.getUpdatedBy(), created.getFamilya()+" "+created.getIsm(), updated.getFamilya()+" "+updated.getIsm(), vozvratTovar.getTotalSumma(), null, vozvratTovar.isTasdiqlandi(), vozvratTovar.getRasxodTovar().getId(), vozvratTovar.getIzoh());
    }

    public VozvratTovarResponseDTO toDto(VozvratTovar vozvratTovar, List<VozvratTovarDetailResponseDTO> vozvratTovarDetails) {
        User created = userService.entity(vozvratTovar.getCreatedBy());
        User updated = userService.entity(vozvratTovar.getUpdatedBy());
        return new VozvratTovarResponseDTO(vozvratTovar.getId(), Utils.formatDate(vozvratTovar.getCreatedAt()), Utils.formatDate(vozvratTovar.getUpdatedAt()), created.getId(), updated.getUpdatedBy(), created.getFamilya()+" "+created.getIsm(), updated.getFamilya()+" "+updated.getIsm(), vozvratTovar.getTotalSumma(), vozvratTovarDetails, vozvratTovar.isTasdiqlandi(), vozvratTovar.getRasxodTovar().getId(), vozvratTovar.getIzoh());
    }

    @Override
    public void update(VozvratTovar vozvratTovar, VozvratTovarRequestDTO requestDTO) {
        vozvratTovar.setIzoh(requestDTO.izoh());
    }
}
