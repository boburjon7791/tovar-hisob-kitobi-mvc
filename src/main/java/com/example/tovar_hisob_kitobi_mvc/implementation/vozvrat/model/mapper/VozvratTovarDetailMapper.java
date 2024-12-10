package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.mapper;

import com.example.tovar_hisob_kitobi_mvc.base.model.mapper.BaseMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.service.VozvratTovarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VozvratTovarDetailMapper implements BaseMapper<VozvratTovarDetail, VozvratTovarRequestDTO, VozvratTovarDetailResponseDTO> {
    private final VozvratTovarService vozvratTovarService;

    @Override
    public VozvratTovarDetail toEntity(VozvratTovarRequestDTO requestDTO) {
        return new VozvratTovarDetail(vozvratTovarService.entity(requestDTO.vozvratTovarId()), null, requestDTO.miqdori(), null);
    }

    @Override
    public VozvratTovarDetailResponseDTO toDto(VozvratTovarDetail vozvratTovarDetail) {
        Tovar tovar = vozvratTovarDetail.getTovar();
        return new VozvratTovarDetailResponseDTO(vozvratTovarDetail.getId(), vozvratTovarDetail.getVozvratTovar().getId(), tovar.getId(), tovar.getNomi(), tovar.getRasmi(), vozvratTovarDetail.getMiqdori(), vozvratTovarDetail.getSumma(), tovar.getShtrixKod());
    }

    @Override
    public void update(VozvratTovarDetail vozvratTovarDetail, VozvratTovarRequestDTO vozvratTovarRequestDTO){
        vozvratTovarDetail.setIzoh(vozvratTovarRequestDTO.izoh());
    }
}
