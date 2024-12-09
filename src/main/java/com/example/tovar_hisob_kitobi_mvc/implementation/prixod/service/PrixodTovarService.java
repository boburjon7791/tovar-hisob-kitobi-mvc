package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.filtering.PrixodTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.mapper.PrixodTovarMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository.PrixodTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Service
@RequiredArgsConstructor
public class PrixodTovarService extends BaseService<PrixodTovar, UUID, PrixodTovarRequestDTO, PrixodTovarResponseDTO, PrixodTovarFiltering> {
    private final PrixodTovarDetailService prixodTovarDetailService;
    private PrixodTovarMapper prixodTovarMapper;
    private final PrixodTovarDetailRepository prixodTovarDetailRepository;
    private final TovarService tovarService;

    @Autowired
    public void setPrixodTovarMapper(@Lazy PrixodTovarMapper prixodTovarMapper) {
        this.prixodTovarMapper = prixodTovarMapper;
    }


    @Override
    public void checkCreating(PrixodTovarRequestDTO prixodTovarRequestDTO) {

    }

    @Override
    public void checkUpdating(PrixodTovarRequestDTO prixodTovarRequestDTO, UUID uuid) {

    }

    public UUID start() {
        return getBaseRepository().save(new PrixodTovar()).getId();
    }

    @Override
    @Transactional
    public ApiResponse<PrixodTovarResponseDTO> create(PrixodTovarRequestDTO requestDTO) {
        prixodTovarDetailService.addPrixodDetail(requestDTO);
        return findById(requestDTO.prixodTovarId());
    }

    public ApiResponse<PrixodTovarResponseDTO> deleteDetail(UUID detailId, UUID prixodTovarId){
        List<PrixodTovarDetail> prixodTovarDetails = prixodTovarDetailRepository.findAllByPrixodTovarId(prixodTovarId);
        AtomicReference<PrixodTovarDetail> atomicReference=new AtomicReference<>();
        prixodTovarDetails.removeIf(prixodTovarDetail -> {
            atomicReference.set(prixodTovarDetail);
            return prixodTovarDetail.getId().equals(detailId);
        });
        PrixodTovarDetail prixodTovarDetail = atomicReference.get();
        if (prixodTovarDetail !=null) {
            PrixodTovar prixodTovar = prixodTovarDetail.getPrixodTovar();
            prixodTovarDetailRepository.delete(prixodTovarDetail);
            prixodTovar.setTotalSumma(prixodTovarDetails.stream().map(PrixodTovarDetail::getSumma).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
            getBaseRepository().saveAndFlush(prixodTovar);
        }
        return findById(prixodTovarId);
    }

    @Transactional
    public ApiResponse<PrixodTovarResponseDTO> end(UUID id) {
        PrixodTovar entity = entity(id);
        if (!entity.isTasdiqlandi()) {
            entity.setTasdiqlandi(true);
            PrixodTovar saved = getBaseRepository().save(entity);
            PrixodTovarResponseDTO responseDTO = getBaseMapper().toDto(saved);
            List<PrixodTovarDetail> prixodTovarDetails = prixodTovarDetailRepository.findAllByPrixodTovarId(id);
            if (prixodTovarDetails.isEmpty()) {
                throw new ApiException(getLocalization().getMessage("tovar_not_for_confirm"));
            }
            prixodTovarDetails.forEach(prixodTovarDetail -> {
                tovarService.addKol(prixodTovarDetail.getTovar(), prixodTovarDetail.getMiqdori());
            });
            return ApiResponse.ok(responseDTO);
        }
        throw new ApiException(getLocalization().getMessage("prixod_allaqachon_tasdiqlangan"));
    }

    @Override
    public ApiResponse<PrixodTovarResponseDTO> findById(UUID id) {
        PrixodTovar prixodTovar = entity(id);
        List<PrixodTovarDetailResponseDTO> prixodTovarDetails = prixodTovarDetailRepository.findAllByPrixodTovarId(id).stream().map(prixodTovarDetail -> prixodTovarDetailService.getBaseMapper().toDto(prixodTovarDetail)).toList();
        PrixodTovarResponseDTO responseDTO = prixodTovarMapper.toDto(prixodTovar, prixodTovarDetails);
        return ApiResponse.ok(responseDTO);
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteById(UUID id) {
        ApiResponse<Void> response = super.deleteById(id);
        prixodTovarDetailService.deleteAllByPrixodTovarId(id);
        return response;
    }
}
