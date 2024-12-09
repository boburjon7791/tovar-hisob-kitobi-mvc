package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.filtering.VozvratTovarFiltering;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VozvratTovarService extends BaseService<VozvratTovar, UUID, VozvratTovarRequestDTO, VozvratTovarResponseDTO, VozvratTovarFiltering> {
    @Override
    public void checkCreating(VozvratTovarRequestDTO vozvratTovarRequestDTO) {

    }

    @Override
    public void checkUpdating(VozvratTovarRequestDTO vozvratTovarRequestDTO, UUID uuid) {

    }

    public UUID start() {
        return getBaseRepository().save(new VozvratTovar()).getId();
    }

    public ApiResponse<VozvratTovarResponseDTO> end(UUID id) {
        VozvratTovar entity = entity(id);
        if (entity.isTasdiqlandi()) {
            entity.setTasdiqlandi(true);
            VozvratTovar saved = getBaseRepository().save(entity);
            VozvratTovarResponseDTO responseDTO = getBaseMapper().toDto(saved);
            return ApiResponse.ok(responseDTO);
        }
        throw new ApiException(getLocalization().getMessage("vozvrat_allaqachon_tasdiqlangan"));
    }
}
