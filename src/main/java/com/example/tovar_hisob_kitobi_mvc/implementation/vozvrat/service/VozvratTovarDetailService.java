package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.service;

import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository.RasxodTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.filtering.VozvratTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository.VozvratTovarDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class VozvratTovarDetailService extends BaseService<VozvratTovarDetail, UUID, VozvratTovarRequestDTO, VozvratTovarDetailResponseDTO, VozvratTovarFiltering> {
    private final TovarService tovarService;
    private VozvratTovarService vozvratTovarService;
    private final VozvratTovarDetailRepository vozvratTovarDetailRepository;
    private final RasxodTovarDetailRepository rasxodTovarDetailRepository;

    @Autowired
    public void setVozvratTovarService(@Lazy VozvratTovarService vozvratTovarService) {
        this.vozvratTovarService = vozvratTovarService;
    }

    @Override
    public void checkCreating(VozvratTovarRequestDTO vozvratTovarRequestDTO) {

    }

    @Override
    public void checkUpdating(VozvratTovarRequestDTO vozvratTovarRequestDTO, UUID uuid) {

    }

    public void addVozvratDetail(VozvratTovarRequestDTO requestDTO) {
        VozvratTovar vozvratTovar = vozvratTovarService.entity(requestDTO.vozvratTovarId());
        if (vozvratTovar.isTasdiqlandi()) {
            throw new ApiException(getLocalization().getMessage("vozvrat_allaqachon_tasdiqlangan"));
        }
        List<VozvratTovarDetail> vozvratTovarDetails = vozvratTovarDetailRepository.findAllByVozvratTovarId(requestDTO.vozvratTovarId(), Sort.unsorted());
        Tovar tovar = tovarService.findByShtrixKod(requestDTO.shtrixKod());
        List<RasxodTovarDetail> rasxodTovars = rasxodTovarDetailRepository.findAllByRasxodTovarId(vozvratTovar.getRasxodTovar().getId(), Sort.unsorted());
        rasxodTovars.stream()
                .filter(rasxodTovarDetail -> rasxodTovarDetail.getTovar().getShtrixKod().equals(requestDTO.shtrixKod()))
                .findFirst()
                .ifPresentOrElse(rasxodTovarDetail -> {
                    if (rasxodTovarDetail.getMiqdori().compareTo(requestDTO.miqdori())<0) {
                        throw new ApiException(getLocalization().getMessage("rasxod_tovar_ostatka_less_than_vozvrat_tovar")+", "+getLocalization().getMessage("rasxod_tovar")+" : "+rasxodTovarDetail.getMiqdori()+", "+getLocalization().getMessage("vozvrat_tovar")+" : "+requestDTO.miqdori());
                    }
                },() -> {
                    throw new ApiException(getLocalization().getMessage("tovar_not_found_in_rasxod"));
                });
        vozvratTovarDetails.stream()
                .filter(vozvratTovarDetail -> vozvratTovarDetail.getTovar().getShtrixKod().equals(requestDTO.shtrixKod()))
                .findFirst()
                .ifPresentOrElse(vozvratTovarDetail -> {
                    vozvratTovarDetail.setMiqdori(requestDTO.miqdori());
                    vozvratTovarDetail.setSumma(requestDTO.miqdori().multiply(tovar.getRasxodSumma()));
                }, () -> {
                    VozvratTovarDetail vozvratTovarDetail = getBaseMapper().toEntity(requestDTO);
                    vozvratTovarDetail.setMiqdori(BigDecimal.ONE);
                    vozvratTovarDetail.setSumma(tovar.getRasxodSumma());
                    vozvratTovarDetail.setVozvratTovar(vozvratTovar);
                    vozvratTovarDetail.setTovar(tovar);
                    vozvratTovarDetails.add(vozvratTovarDetail);
                });
        vozvratTovarDetailRepository.saveAllAndFlush(vozvratTovarDetails);
        vozvratTovar.setTotalSumma(vozvratTovarDetails.stream().map(VozvratTovarDetail::getSumma).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
        vozvratTovarService.saveEntity(vozvratTovar);
        getEntityManager().flush();
    }

    public void deleteAllByVozvratTovarId(UUID vozvratTovarId) {
        vozvratTovarDetailRepository.findAllByVozvratTovarId(vozvratTovarId, Sort.unsorted())
                .forEach(vozvratTovarDetail -> {
                    vozvratTovarDetail.setDeleted(true);
                    vozvratTovarDetailRepository.save(vozvratTovarDetail);
                });
    }
}
