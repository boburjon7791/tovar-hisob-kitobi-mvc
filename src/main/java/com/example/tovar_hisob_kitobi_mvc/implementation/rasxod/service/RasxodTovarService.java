package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.filtering.RasxodTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.mapper.RasxodTovarMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository.RasxodTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
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
public class RasxodTovarService extends BaseService<RasxodTovar, UUID, RasxodTovarRequestDTO, RasxodTovarResponseDTO, RasxodTovarFiltering> {
    private final RasxodTovarDetailService rasxodTovarDetailService;
    private RasxodTovarMapper rasxodTovarMapper;
    private final RasxodTovarDetailRepository rasxodTovarDetailRepository;
    private final TovarService tovarService;

    @Autowired
    public void setRasxodTovarMapper(@Lazy RasxodTovarMapper rasxodTovarMapper) {
        this.rasxodTovarMapper = rasxodTovarMapper;
    }

    @Override
    public void checkCreating(RasxodTovarRequestDTO rasxodTovarRequestDTO) {

    }

    @Override
    public void checkUpdating(RasxodTovarRequestDTO rasxodTovarRequestDTO, UUID uuid) {

    }

    public UUID start() {
        return getBaseRepository().save(new RasxodTovar()).getId();
    }

    @Override
    @Transactional
    public ApiResponse<RasxodTovarResponseDTO> create(RasxodTovarRequestDTO requestDTO) {
        rasxodTovarDetailService.addRasxodDetail(requestDTO);
        return findById(requestDTO.rasxodTovarId());
    }

    @Transactional
    public ApiResponse<RasxodTovarResponseDTO> end(UUID id) {
        RasxodTovar entity = entity(id);
        if (!entity.isTasdiqlandi()) {
            entity.setTasdiqlandi(true);
            RasxodTovar saved = getBaseRepository().save(entity);
            RasxodTovarResponseDTO responseDTO = getBaseMapper().toDto(saved);
            List<RasxodTovarDetail> rasxodTovarDetails = rasxodTovarDetailRepository.findAllByRasxodTovarId(id);
            if (rasxodTovarDetails.isEmpty()) {
                throw new ApiException(getLocalization().getMessage("tovar_not_for_confirm"));
            }
            rasxodTovarDetails.forEach(rasxodTovarDetail -> {
                tovarService.subtractKol(rasxodTovarDetail.getTovar(), rasxodTovarDetail.getMiqdori());
            });
            return ApiResponse.ok(responseDTO);
        }
        throw new ApiException(getLocalization().getMessage("rasxod_allaqachon_tasdiqlangan"));
    }

    @Transactional
    public ApiResponse<RasxodTovarResponseDTO> deleteDetail(UUID detailId, UUID rasxodTovarId) {
        List<RasxodTovarDetail> rasxodTovarDetails = rasxodTovarDetailRepository.findAllByRasxodTovarId(rasxodTovarId);
        AtomicReference<RasxodTovarDetail> atomicReference=new AtomicReference<>();
        rasxodTovarDetails.removeIf(rasxodTovarDetail -> {
            atomicReference.set(rasxodTovarDetail);
            return rasxodTovarDetail.getId().equals(detailId);
        });
        RasxodTovarDetail rasxodTovarDetail = atomicReference.get();
        if (rasxodTovarDetail !=null) {
            RasxodTovar rasxodTovar = rasxodTovarDetail.getRasxodTovar();
            rasxodTovarDetailRepository.delete(rasxodTovarDetail);
            rasxodTovar.setTotalSumma(rasxodTovarDetails.stream().map(RasxodTovarDetail::getSumma).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
            getBaseRepository().saveAndFlush(rasxodTovar);
        }
        return findById(rasxodTovarId);
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteById(UUID id) {
        ApiResponse<Void> response = super.deleteById(id);
        rasxodTovarDetailService.deleteAllByRasxodTovarId(id);
        return response;
    }

    @Override
    public ApiResponse<RasxodTovarResponseDTO> findById(UUID id) {
        RasxodTovar rasxodTovar = entity(id);
        List<RasxodTovarDetailResponseDTO> rasxodTovarDetails = rasxodTovarDetailRepository.findAllByRasxodTovarId(id).stream().map(rasxodTovarDetail -> rasxodTovarDetailService.getBaseMapper().toDto(rasxodTovarDetail)).toList();
        RasxodTovarResponseDTO responseDTO = rasxodTovarMapper.toDto(rasxodTovar, rasxodTovarDetails);
        return ApiResponse.ok(responseDTO);
    }
}