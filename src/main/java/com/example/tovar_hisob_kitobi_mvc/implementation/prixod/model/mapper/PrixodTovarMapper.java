package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.mapper;

import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.model.mapper.BaseMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
@RequiredArgsConstructor
public class PrixodTovarMapper implements BaseMapper<PrixodTovar, PrixodTovarRequestDTO, PrixodTovarResponseDTO> {
    private final UserService userService;
    @Override
    public void update(PrixodTovar prixodTovar, PrixodTovarRequestDTO prixodTovarRequestDTO){
        prixodTovar.setIzoh(prixodTovarRequestDTO.izoh());
    }

    @Override
    public PrixodTovarResponseDTO toDto(PrixodTovar prixodTovar){
        User createdBy = userService.entity(prixodTovar.getCreatedBy());
        User updatedBy = userService.entity(prixodTovar.getUpdatedBy());
        return new PrixodTovarResponseDTO(prixodTovar.getId(), Utils.formatDate(prixodTovar.getCreatedAt()), Utils.formatDate(prixodTovar.getUpdatedAt()), prixodTovar.getCreatedBy(), prixodTovar.getUpdatedBy(), String.format("%s %s", createdBy.getFamilya(), createdBy.getIsm()), String.format("%s %s", updatedBy.getFamilya(), updatedBy.getIsm()), prixodTovar.getTotalSumma(), null, prixodTovar.isTasdiqlandi());
    }

    public PrixodTovarResponseDTO toDto(PrixodTovar prixodTovar, List<PrixodTovarDetailResponseDTO> prixodTovarDetails){
        User createdBy = userService.entity(prixodTovar.getCreatedBy());
        User updatedBy = userService.entity(prixodTovar.getUpdatedBy());
        return new PrixodTovarResponseDTO(prixodTovar.getId(), Utils.formatDate(prixodTovar.getCreatedAt()), Utils.formatDate(prixodTovar.getUpdatedAt()), prixodTovar.getCreatedBy(), prixodTovar.getUpdatedBy(), String.format("%s %s", createdBy.getFamilya(), createdBy.getIsm()), String.format("%s %s", updatedBy.getFamilya(), updatedBy.getIsm()), prixodTovar.getTotalSumma(), prixodTovarDetails, prixodTovar.isTasdiqlandi());
    }

    @Override
    public PrixodTovar toEntity(PrixodTovarRequestDTO requestDTO){
        PrixodTovar prixodTovar = new PrixodTovar();
        prixodTovar.setIzoh(requestDTO.izoh());
        return prixodTovar;
    }
}
