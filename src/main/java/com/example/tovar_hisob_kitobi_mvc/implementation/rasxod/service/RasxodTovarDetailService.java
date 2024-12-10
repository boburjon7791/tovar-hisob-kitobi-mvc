package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.service;

import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.filtering.RasxodTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository.RasxodTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RasxodTovarDetailService extends BaseService<RasxodTovarDetail, UUID, RasxodTovarRequestDTO, RasxodTovarDetailResponseDTO, RasxodTovarFiltering> {
    private final TovarService tovarService;
    private RasxodTovarService rasxodTovarService;
    private final RasxodTovarDetailRepository rasxodTovarDetailRepository;

    @Autowired
    public void setRasxodTovarService(@Lazy RasxodTovarService rasxodTovarService) {
        this.rasxodTovarService = rasxodTovarService;
    }

    @Override
    public void checkCreating(RasxodTovarRequestDTO rasxodTovarRequestDTO) {

    }

    @Override
    public void checkUpdating(RasxodTovarRequestDTO rasxodTovarRequestDTO, UUID uuid) {

    }

    public void addRasxodDetail(RasxodTovarRequestDTO requestDTO) {
        RasxodTovar rasxodTovar = rasxodTovarService.entity(requestDTO.rasxodTovarId());
        if (rasxodTovar.isTasdiqlandi()) {
            throw new ApiException(getLocalization().getMessage("rasxod_allaqachon_tasdiqlangan"));
        }
        Tovar tovar = tovarService.findByShtrixKod(requestDTO.shtrixKod());
        List<RasxodTovarDetail> rasxodTovarDetails = rasxodTovarDetailRepository.findAllByRasxodTovarId(requestDTO.rasxodTovarId(), Sort.unsorted());
        rasxodTovarDetails.stream()
                .filter(rasxodTovarDetail -> rasxodTovarDetail.getTovar().getShtrixKod().equals(requestDTO.shtrixKod()))
                .findFirst()
                .ifPresentOrElse(prixodTovarDetail -> {
                    prixodTovarDetail.setMiqdori(requestDTO.miqdori());
                    prixodTovarDetail.setSumma(requestDTO.miqdori().multiply(tovar.getRasxodSumma()));
                }, () -> {
                    RasxodTovarDetail rasxodTovarDetail = getBaseMapper().toEntity(requestDTO);
                    rasxodTovarDetail.setMiqdori(BigDecimal.ONE);
                    rasxodTovarDetail.setSumma(tovar.getRasxodSumma());
                    rasxodTovarDetail.setRasxodTovar(rasxodTovar);
                    rasxodTovarDetail.setTovar(tovar);
                    rasxodTovarDetails.add(rasxodTovarDetail);
                });
        rasxodTovarDetailRepository.saveAllAndFlush(rasxodTovarDetails);
        rasxodTovar.setTotalSumma(rasxodTovarDetails.stream().map(RasxodTovarDetail::getSumma).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
        rasxodTovarService.saveEntity(rasxodTovar);
        getEntityManager().flush();
    }

    public void deleteAllByRasxodTovarId(UUID rasxodTovarId) {
        rasxodTovarDetailRepository.findAllByRasxodTovarId(rasxodTovarId, Sort.unsorted())
                .forEach(rasxodTovarDetail -> {
                    rasxodTovarDetail.setDeleted(true);
                    rasxodTovarDetailRepository.save(rasxodTovarDetail);
                });
    }
}
