package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.service.RasxodTovarService;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.filtering.VozvratTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.mapper.VozvratTovarMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository.VozvratTovarDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class VozvratTovarService extends BaseService<VozvratTovar, UUID, VozvratTovarRequestDTO, VozvratTovarResponseDTO, VozvratTovarFiltering> {
    private final VozvratTovarDetailService vozvratTovarDetailService;
    private VozvratTovarMapper vozvratTovarMapper;
    private final VozvratTovarDetailRepository vozvratTovarDetailRepository;
    private final TovarService tovarService;
    private final RasxodTovarService rasxodTovarService;

    @Autowired
    public void setVozvratTovarMapper(@Lazy VozvratTovarMapper vozvratTovarMapper) {
        this.vozvratTovarMapper = vozvratTovarMapper;
    }

    @Override
    public void checkCreating(VozvratTovarRequestDTO vozvratTovarRequestDTO) {

    }

    @Override
    public void checkUpdating(VozvratTovarRequestDTO vozvratTovarRequestDTO, UUID uuid) {

    }

    public UUID start(UUID rasxodTovarId) {
        VozvratTovar vozvratTovar = new VozvratTovar();
        RasxodTovar rasxodTovar = rasxodTovarService.entity(rasxodTovarId);
        vozvratTovar.setRasxodTovar(rasxodTovar);
        return getBaseRepository().save(vozvratTovar).getId();
    }

    @Override
    @Transactional
    public ApiResponse<VozvratTovarResponseDTO> create(VozvratTovarRequestDTO requestDTO) {
        vozvratTovarDetailService.addVozvratDetail(requestDTO);
        return findById(requestDTO.vozvratTovarId());
    }

    @Transactional
    public ApiResponse<VozvratTovarResponseDTO> end(UUID id) {
        VozvratTovar entity = entity(id);
        if (entity.getIzoh()==null || entity.getIzoh().isBlank()) {
            throw new ApiException(getLocalization().getMessage("write_izoh"));
        }
        if (!entity.isTasdiqlandi()) {
            entity.setTasdiqlandi(true);
            VozvratTovar saved = getBaseRepository().save(entity);
            VozvratTovarResponseDTO responseDTO = getBaseMapper().toDto(saved);
            List<VozvratTovarDetail> vozvratTovarDetails = vozvratTovarDetailRepository.findAllByVozvratTovarId(id, Utils.sortByCreatedAtDesc());
            if (vozvratTovarDetails.isEmpty()) {
                throw new ApiException(getLocalization().getMessage("tovar_not_for_confirm"));
            }
            vozvratTovarDetails.forEach(vozvratTovarDetail -> {
                tovarService.addKol(vozvratTovarDetail.getTovar(), vozvratTovarDetail.getMiqdori());
            });
            return ApiResponse.ok(responseDTO);
        }
        throw new ApiException(getLocalization().getMessage("vozvrat_allaqachon_tasdiqlangan"));
    }

    @Transactional
    public ApiResponse<VozvratTovarResponseDTO> deleteDetail(UUID detailId, UUID vozvratTovarId) {
        List<VozvratTovarDetail> vozvratTovarDetails = vozvratTovarDetailRepository.findAllByVozvratTovarId(vozvratTovarId, Utils.sortByCreatedAtDesc());
        AtomicReference<VozvratTovarDetail> atomicReference=new AtomicReference<>();
        vozvratTovarDetails.removeIf(vozvratTovarDetail -> {
            atomicReference.set(vozvratTovarDetail);
            return vozvratTovarDetail.getId().equals(detailId);
        });
        VozvratTovarDetail vozvratTovarDetail = atomicReference.get();
        if (vozvratTovarDetail !=null) {
            VozvratTovar vozvratTovar = vozvratTovarDetail.getVozvratTovar();
            vozvratTovarDetailRepository.delete(vozvratTovarDetail);
            vozvratTovar.setTotalSumma(vozvratTovarDetails.stream().map(VozvratTovarDetail::getSumma).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
            getBaseRepository().saveAndFlush(vozvratTovar);
        }
        return findById(vozvratTovarId);
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteById(UUID id) {
        vozvratTovarDetailService.deleteAllByVozvratTovarId(id);
        VozvratTovar vozvratTovar = entity(id);
        vozvratTovar.setDeleted(true);
        getBaseRepository().save(vozvratTovar);
        return ApiResponse.ok();
    }

    @Override
    public ApiResponse<VozvratTovarResponseDTO> findById(UUID id) {
        VozvratTovar vozvratTovar = entity(id);
        List<VozvratTovarDetailResponseDTO> vozvratTovarDetails = vozvratTovarDetailRepository.findAllByVozvratTovarId(id, Utils.sortByCreatedAtDesc()).stream().map(vozvratTovarDetail -> vozvratTovarDetailService.getBaseMapper().toDto(vozvratTovarDetail)).toList();
        VozvratTovarResponseDTO responseDTO = vozvratTovarMapper.toDto(vozvratTovar, vozvratTovarDetails);
        return ApiResponse.ok(responseDTO);
    }
}
