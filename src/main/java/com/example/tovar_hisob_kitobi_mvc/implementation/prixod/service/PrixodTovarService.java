package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.common.WebSocketMessageBrokerConfig;
import com.example.tovar_hisob_kitobi_mvc.base.common.WebSocketResponse;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.home.controller.HomeController;
import com.example.tovar_hisob_kitobi_mvc.implementation.home.service.HomeService;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.*;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.filtering.PrixodTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.mapper.PrixodTovarMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSummaByCreatedBy;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository.PrixodTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository.PrixodTovarRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;
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
    private final PrixodTovarRepository prixodTovarRepository;
    private final HomeService homeService;

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
        List<PrixodTovarDetail> prixodTovarDetails = prixodTovarDetailRepository.findAllByPrixodTovarId(prixodTovarId, Sort.unsorted());
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
            List<PrixodTovarDetail> prixodTovarDetails = prixodTovarDetailRepository.findAllByPrixodTovarId(id, Sort.unsorted());
            if (prixodTovarDetails.isEmpty()) {
                throw new ApiException(getLocalization().getMessage("tovar_not_for_confirm"));
            }
            prixodTovarDetails.forEach(prixodTovarDetail -> {
                tovarService.addKol(prixodTovarDetail.getTovar(), prixodTovarDetail.getMiqdori());
            });
            changeHomeValue();
            return ApiResponse.ok(responseDTO);
        }
        throw new ApiException(getLocalization().getMessage("prixod_allaqachon_tasdiqlangan"));
    }

    @Override
    public ApiResponse<PrixodTovarResponseDTO> findById(UUID id) {
        PrixodTovar prixodTovar = entity(id);
        List<PrixodTovarDetailResponseDTO> prixodTovarDetails = prixodTovarDetailRepository.findAllByPrixodTovarId(id, Utils.sortByCreatedAtDesc()).stream().map(prixodTovarDetail -> prixodTovarDetailService.getBaseMapper().toDto(prixodTovarDetail)).toList();
        PrixodTovarResponseDTO responseDTO = prixodTovarMapper.toDto(prixodTovar, prixodTovarDetails);
        return ApiResponse.ok(responseDTO);
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteById(UUID id) {
        prixodTovarDetailService.deleteAllByPrixodTovarId(id);
        PrixodTovar prixodTovar = entity(id);
        prixodTovar.setDeleted(true);
        getBaseRepository().save(prixodTovar);
        return ApiResponse.ok();
    }

    private void changeHomeValue(){
        int nowYear = Year.now().getValue();

        List<PrixodSumma> prixodSummaProjectionList = prixodTovarRepository.findAllPrixodSummaByYear(nowYear);

        List<PrixodSummaDTO> prixodSummaListUz = prixodSummaProjectionList.stream().map(prixodSumma -> PrixodSummaDTO.of(prixodSumma, getLocalization(), getUzLocale())).toList();
        List<PrixodSummaDTO> prixodSummaListRu = prixodSummaProjectionList.stream().map(prixodSumma -> PrixodSummaDTO.of(prixodSumma, getLocalization(), getRuLocale())).toList();
        getSimpMessagingTemplate().convertAndSend(WebSocketMessageBrokerConfig._prixod+"/"+getUzLocale().getLanguage(), WebSocketResponse.ofPrixod(prixodSummaListUz));
        getSimpMessagingTemplate().convertAndSend(WebSocketMessageBrokerConfig._prixod+"/"+getRuLocale().getLanguage(), WebSocketResponse.ofPrixod(prixodSummaListRu));

        List<PrixodSummaByCreatedBy> prixodSummaByCreatedByProjectionList = prixodTovarRepository.findAllPrixodSummaByCreatedByByYear(nowYear);

        List<PrixodSummaByCreatedByDTO> prixodSummaByCreatedByListUz = prixodSummaByCreatedByProjectionList.stream().map(prixodSummaByCreatedBy -> PrixodSummaByCreatedByDTO.of(prixodSummaByCreatedBy, getLocalization(), getUzLocale())).toList();
        List<PrixodSummaByCreatedByDTO> prixodSummaByCreatedByListRu = prixodSummaByCreatedByProjectionList.stream().map(prixodSummaByCreatedBy -> PrixodSummaByCreatedByDTO.of(prixodSummaByCreatedBy, getLocalization(), getRuLocale())).toList();
        getSimpMessagingTemplate().convertAndSend(WebSocketMessageBrokerConfig._prixodByCreated+"/"+getUzLocale().getLanguage(), WebSocketResponse.ofPrixodCreated(prixodSummaByCreatedByListUz));
        getSimpMessagingTemplate().convertAndSend(WebSocketMessageBrokerConfig._prixodByCreated+"/"+getRuLocale().getLanguage(), WebSocketResponse.ofPrixodCreated(prixodSummaByCreatedByListRu));
    }
}
