package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.mapper;

import com.example.tovar_hisob_kitobi_mvc.base.model.mapper.BaseMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrixodTovarDetailMapper implements BaseMapper<PrixodTovarDetail, PrixodTovarRequestDTO, PrixodTovarDetailResponseDTO> {
    private final TovarService tovarService;
    @Override
    public PrixodTovarDetail toEntity(PrixodTovarRequestDTO requestDTO) {
        return new PrixodTovarDetail(null, null, requestDTO.miqdori(), null);
    }

    @Override
    public PrixodTovarDetailResponseDTO toDto(PrixodTovarDetail prixodTovarDetail) {
        Tovar tovar = prixodTovarDetail.getTovar();
        return new PrixodTovarDetailResponseDTO(prixodTovarDetail.getId(), prixodTovarDetail.getPrixodTovar().getId(), tovar.getId(), tovar.getNomi(), tovar.getRasmi(), prixodTovarDetail.getMiqdori(), prixodTovarDetail.getSumma(), tovar.getShtrixKod());
    }

    @Override
    public void update(PrixodTovarDetail prixodTovarDetail, PrixodTovarRequestDTO requestDTO){
        Tovar tovar = tovarService.entity(requestDTO.tovarId());
        prixodTovarDetail.setMiqdori(requestDTO.miqdori());
        prixodTovarDetail.setSumma(requestDTO.miqdori().multiply(tovar.getPrixodSumma()));
    }
}
