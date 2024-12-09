package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.mapper;

import com.example.tovar_hisob_kitobi_mvc.base.model.mapper.BaseMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.service.RasxodTovarService;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RasxodTovarDetailMapper implements BaseMapper<RasxodTovarDetail, RasxodTovarRequestDTO, RasxodTovarDetailResponseDTO> {
    private final RasxodTovarService rasxodTovarService;
    private final TovarService tovarService;
    @Override
    public RasxodTovarDetail toEntity(RasxodTovarRequestDTO requestDTO) {
        return new RasxodTovarDetail(rasxodTovarService.entity(requestDTO.rasxodTovarId()), null, requestDTO.miqdori(), null);
    }

    @Override
    public RasxodTovarDetailResponseDTO toDto(RasxodTovarDetail rasxodTovarDetail) {
        Tovar tovar = tovarService.entity(rasxodTovarDetail.getTovar().getId());
        return new RasxodTovarDetailResponseDTO(rasxodTovarDetail.getId(), rasxodTovarDetail.getRasxodTovar().getId(), tovar.getId(), tovar.getNomi(), tovar.getRasmi(), rasxodTovarDetail.getMiqdori(), rasxodTovarDetail.getSumma(), tovar.getShtrixKod());
    }

    @Override
    public void update(RasxodTovarDetail rasxodTovarDetail, RasxodTovarRequestDTO rasxodTovarRequestDTO){
        rasxodTovarDetail.setIzoh(rasxodTovarRequestDTO.izoh());
    }
}
