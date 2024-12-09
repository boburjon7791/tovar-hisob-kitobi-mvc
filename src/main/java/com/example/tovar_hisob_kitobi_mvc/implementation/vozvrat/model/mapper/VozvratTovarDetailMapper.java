package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.mapper;

import com.example.tovar_hisob_kitobi_mvc.base.model.mapper.BaseMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovarDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VozvratTovarDetailMapper extends BaseMapper<VozvratTovarDetail, VozvratTovarRequestDTO, VozvratTovarDetailResponseDTO> {
    @Override
    default void update(VozvratTovarDetail vozvratTovarDetail, VozvratTovarRequestDTO vozvratTovarRequestDTO){
    }
}
