package com.example.tovar_hisob_kitobi_mvc.implementation.home.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.common.internationalization.Localization;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.implementation.home.controller.HomeController;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodSummaByCreatedByDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodSummaDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSummaByCreatedBy;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository.PrixodTovarRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodSummaByCreatedByDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodSummaDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSummaByCreatedBy;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository.RasxodTovarRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratSummaByCreatedByDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratSummaDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.projection.VozvratSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.projection.VozvratSummaByCreatedBy;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository.VozvratTovarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final PrixodTovarRepository prixodTovarRepository;
    private final RasxodTovarRepository rasxodTovarRepository;
    private final VozvratTovarRepository vozvratTovarRepository;
    private final Localization localization;

    public ApiResponse<List<PrixodSummaDTO>> prixodSummaByYear(int year){
        return ApiResponse.ok(prixodTovarRepository.findAllPrixodSummaByYear(year).stream().map(prixodSumma -> PrixodSummaDTO.of(prixodSumma, localization)).toList());
    }

    public ApiResponse<List<PrixodSummaByCreatedByDTO>> prixodSummaByCreatedByYear(int year){
        return ApiResponse.ok(prixodTovarRepository.findAllPrixodSummaByCreatedByByYear(year).stream().map(prixodSummaByCreatedBy -> PrixodSummaByCreatedByDTO.of(prixodSummaByCreatedBy, localization)).toList());
    }

    public ApiResponse<List<RasxodSummaDTO>> rasxodSummaByYear(int year){
        return ApiResponse.ok(rasxodTovarRepository.findAllRasxodSummaByYear(year).stream().map(rasxodSumma -> RasxodSummaDTO.of(rasxodSumma, localization)).toList());
    }

    public ApiResponse<List<RasxodSummaByCreatedByDTO>> rasxodSummaByCreatedByYear(int year){
        return ApiResponse.ok(rasxodTovarRepository.findAllRasxodSummaByCreatedByByYear(year).stream().map(rasxodSummaByCreatedBy -> RasxodSummaByCreatedByDTO.of(rasxodSummaByCreatedBy, localization)).toList());
    }

    public ApiResponse<List<VozvratSummaDTO>> vozvratSummaByYear(int year){
        return ApiResponse.ok(vozvratTovarRepository.findAllVozvratSummaByYear(year).stream().map(vozvratSumma -> VozvratSummaDTO.of(vozvratSumma, localization)).toList());
    }

    public ApiResponse<List<VozvratSummaByCreatedByDTO>> vozvratSummaByCreatedByYear(int year){
        return ApiResponse.ok(vozvratTovarRepository.findAllVozvratSummaByCreatedByByYear(year).stream().map(vozvratSummaByCreatedBy -> VozvratSummaByCreatedByDTO.of(vozvratSummaByCreatedBy, localization)).toList());
    }

    public ApiResponse<?> home(String type, int year, Model model){
        return switch (type){
            case HomeController._prixodSumma -> {
                ApiResponse<List<PrixodSummaDTO>> response = prixodSummaByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(PrixodSummaDTO::sum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            case HomeController._rasxodSumma -> {
                ApiResponse<List<RasxodSummaDTO>> response = rasxodSummaByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(RasxodSummaDTO::sum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            case HomeController._vozvratSumma -> {
                ApiResponse<List<VozvratSummaDTO>> response = vozvratSummaByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(VozvratSummaDTO::sum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            case HomeController._prixodSummaByCreated -> {
                ApiResponse<List<PrixodSummaByCreatedByDTO>> response = prixodSummaByCreatedByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(PrixodSummaByCreatedByDTO::sum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            case HomeController._rasxodSummaByCreated -> {
                ApiResponse<List<RasxodSummaByCreatedByDTO>> response = rasxodSummaByCreatedByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(RasxodSummaByCreatedByDTO::sum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            case HomeController._vozvratSummaByCreated -> {
                ApiResponse<List<VozvratSummaByCreatedByDTO>> response = vozvratSummaByCreatedByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(VozvratSummaByCreatedByDTO::sum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            default -> throw new ApiException("");
        };
    }
}
