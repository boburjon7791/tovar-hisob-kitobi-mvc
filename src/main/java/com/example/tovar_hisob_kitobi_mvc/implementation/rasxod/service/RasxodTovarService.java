package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.common.WebSocketMessageBrokerConfig;
import com.example.tovar_hisob_kitobi_mvc.base.common.WebSocketResponse;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.home.service.HomeService;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.*;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.filtering.RasxodTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.mapper.RasxodTovarMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSummaByCreatedBy;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository.RasxodTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository.RasxodTovarRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;
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
    private final RasxodTovarRepository rasxodTovarRepository;
    private final HomeService homeService;

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
            List<RasxodTovarDetail> rasxodTovarDetails = rasxodTovarDetailRepository.findAllByRasxodTovarId(id, Sort.unsorted());
            if (rasxodTovarDetails.isEmpty()) {
                throw new ApiException(getLocalization().getMessage("tovar_not_for_confirm"));
            }
            rasxodTovarDetails.forEach(rasxodTovarDetail -> {
                tovarService.subtractKol(rasxodTovarDetail.getTovar(), rasxodTovarDetail.getMiqdori());
            });
            changeHomeValue();
            return ApiResponse.ok(responseDTO);
        }
        throw new ApiException(getLocalization().getMessage("rasxod_allaqachon_tasdiqlangan"));
    }

    @Transactional
    public ApiResponse<RasxodTovarResponseDTO> deleteDetail(UUID detailId, UUID rasxodTovarId) {
        List<RasxodTovarDetail> rasxodTovarDetails = rasxodTovarDetailRepository.findAllByRasxodTovarId(rasxodTovarId, Sort.unsorted());
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
        rasxodTovarDetailService.deleteAllByRasxodTovarId(id);
        RasxodTovar rasxodTovar = entity(id);
        if (rasxodTovar.isTasdiqlandi()) {
            throw new ApiException(getLocalization().getMessage("tasdiqlangan_tranzaksiyani_ochirib_bolmaydi"));
        }
        rasxodTovar.setDeleted(true);
        getBaseRepository().save(rasxodTovar);
        return ApiResponse.ok();
    }

    @Override
    public ApiResponse<RasxodTovarResponseDTO> findById(UUID id) {
        RasxodTovar rasxodTovar = entity(id);
        List<RasxodTovarDetailResponseDTO> rasxodTovarDetails = rasxodTovarDetailRepository.findAllByRasxodTovarId(id, Utils.sortByCreatedAtDesc()).stream().map(rasxodTovarDetail -> rasxodTovarDetailService.getBaseMapper().toDto(rasxodTovarDetail)).toList();
        RasxodTovarResponseDTO responseDTO = rasxodTovarMapper.toDto(rasxodTovar, rasxodTovarDetails);
        return ApiResponse.ok(responseDTO);
    }

    private void changeHomeValue(){
        int nowYear = Year.now().getValue();

        List<RasxodSumma> rasxodSummaProjectionList = rasxodTovarRepository.findAllRasxodSummaByYear(nowYear);

        List<RasxodSummaDTO> rasxodSummaListUz = rasxodSummaProjectionList.stream().map(rasxodSumma -> RasxodSummaDTO.of(rasxodSumma, getLocalization(), getUzLocale())).toList();
        List<RasxodSummaDTO> rasxodSummaListRu = rasxodSummaProjectionList.stream().map(rasxodSumma -> RasxodSummaDTO.of(rasxodSumma, getLocalization(), getRuLocale())).toList();
        getSimpMessagingTemplate().convertAndSend(WebSocketMessageBrokerConfig._rasxod+"/"+getUzLocale().getLanguage(), WebSocketResponse.ofRasxod(rasxodSummaListUz));
        getSimpMessagingTemplate().convertAndSend(WebSocketMessageBrokerConfig._rasxod+"/"+getRuLocale(), WebSocketResponse.ofRasxod(rasxodSummaListRu));

        List<RasxodSummaByCreatedBy> rasxodSummaByCreatedByProjectionList = rasxodTovarRepository.findAllRasxodSummaByCreatedByByYear(nowYear);

        List<RasxodSummaByCreatedByDTO> rasxodSummaByCreatedByListUz = rasxodSummaByCreatedByProjectionList.stream().map(rasxodSummaByCreatedBy -> RasxodSummaByCreatedByDTO.of(rasxodSummaByCreatedBy, getLocalization(), getUzLocale())).toList();
        List<RasxodSummaByCreatedByDTO> rasxodSummaByCreatedByListRu = rasxodSummaByCreatedByProjectionList.stream().map(rasxodSummaByCreatedBy -> RasxodSummaByCreatedByDTO.of(rasxodSummaByCreatedBy, getLocalization(), getRuLocale())).toList();
        getSimpMessagingTemplate().convertAndSend(WebSocketMessageBrokerConfig._rasxodByCreated+"/"+getUzLocale().getLanguage(), WebSocketResponse.ofRasxodCreated(rasxodSummaByCreatedByListUz));
        getSimpMessagingTemplate().convertAndSend(WebSocketMessageBrokerConfig._rasxodByCreated+"/"+getRuLocale().getLanguage(), WebSocketResponse.ofRasxodCreated(rasxodSummaByCreatedByListRu));
    }
}
