package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.mapper;

import com.example.tovar_hisob_kitobi_mvc.base.model.mapper.BaseMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface VozvratTovarMapper extends BaseMapper<VozvratTovar, VozvratTovarRequestDTO, VozvratTovarResponseDTO> {
    @Override
    default void update(VozvratTovar vozvratTovar, VozvratTovarRequestDTO vozvratTovarRequestDTO){

    }
}
