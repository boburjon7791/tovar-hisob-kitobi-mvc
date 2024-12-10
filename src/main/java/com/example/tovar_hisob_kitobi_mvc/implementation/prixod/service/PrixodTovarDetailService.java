package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarDetailResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovarDetail;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.filtering.PrixodTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository.PrixodTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrixodTovarDetailService extends BaseService<PrixodTovarDetail, UUID, PrixodTovarRequestDTO, PrixodTovarDetailResponseDTO, PrixodTovarFiltering> {
    private final TovarService tovarService;
    private PrixodTovarService prixodTovarService;
    private final PrixodTovarDetailRepository prixodTovarDetailRepository;

    @Autowired
    public void setPrixodTovarService(@Lazy PrixodTovarService prixodTovarService) {
        this.prixodTovarService = prixodTovarService;
    }

    @Override
    public void checkCreating(PrixodTovarRequestDTO prixodTovarRequestDTO) {

    }

    @Override
    public void checkUpdating(PrixodTovarRequestDTO prixodTovarRequestDTO, UUID uuid) {

    }

    public void addPrixodDetail(PrixodTovarRequestDTO requestDTO){
        PrixodTovar prixodTovar = prixodTovarService.entity(requestDTO.prixodTovarId());
        if (prixodTovar.isTasdiqlandi()) {
            throw new ApiException(getLocalization().getMessage("prixod_allaqachon_tasdiqlangan"));
        }
        Tovar tovar = tovarService.findByShtrixKod(requestDTO.shtrixKod());
        List<PrixodTovarDetail> prixodTovarDetails = prixodTovarDetailRepository.findAllByPrixodTovarId(requestDTO.prixodTovarId(), Sort.unsorted());
        prixodTovarDetails.stream()
                .filter(prixodTovarDetail -> prixodTovarDetail.getTovar().getShtrixKod().equals(requestDTO.shtrixKod()))
                .findFirst()
                .ifPresentOrElse(prixodTovarDetail -> {
                    prixodTovarDetail.setMiqdori(requestDTO.miqdori());
                    prixodTovarDetail.setSumma(requestDTO.miqdori().multiply(tovar.getPrixodSumma()));
                }, () -> {
                    PrixodTovarDetail prixodTovarDetail = getBaseMapper().toEntity(requestDTO);
                    prixodTovarDetail.setMiqdori(BigDecimal.ONE);
                    prixodTovarDetail.setSumma(tovar.getPrixodSumma());
                    prixodTovarDetail.setPrixodTovar(prixodTovar);
                    prixodTovarDetail.setTovar(tovar);
                    prixodTovarDetails.add(prixodTovarDetail);
                });
        prixodTovarDetailRepository.saveAllAndFlush(prixodTovarDetails);
        prixodTovar.setTotalSumma(prixodTovarDetails.stream().map(PrixodTovarDetail::getSumma).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
        prixodTovarService.saveEntity(prixodTovar);
        getEntityManager().flush();
    }

    public void deleteAllByPrixodTovarId(UUID prixodTovarId) {
        prixodTovarService.getPrixodTovarDetailRepository().findAllByPrixodTovarId(prixodTovarId, Sort.unsorted())
                .forEach(prixodTovarDetail -> {
                    prixodTovarDetail.setDeleted(true);
                    prixodTovarService.getPrixodTovarDetailRepository().save(prixodTovarDetail);
                });
    }
}
