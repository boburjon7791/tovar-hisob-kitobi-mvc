package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.service;

import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.filtering.VozvratTovarFiltering;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VozvratTovarDetailService extends BaseService<VozvratTovarDetail, UUID, VozvratTovarRequestDTO, VozvratTovarDetailResponseDTO, VozvratTovarFiltering> {

    @Override
    public void checkCreating(VozvratTovarRequestDTO vozvratTovarRequestDTO) {

    }

    @Override
    public void checkUpdating(VozvratTovarRequestDTO vozvratTovarRequestDTO, UUID uuid) {

    }
}
