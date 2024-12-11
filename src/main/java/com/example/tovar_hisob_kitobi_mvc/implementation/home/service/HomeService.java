package com.example.tovar_hisob_kitobi_mvc.implementation.home.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.implementation.home.controller.HomeController;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSummaByCreatedBy;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository.PrixodTovarRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSummaByCreatedBy;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository.RasxodTovarRepository;
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

    public ApiResponse<List<PrixodSumma>> prixodSummaByYear(int year){
        return ApiResponse.ok(prixodTovarRepository.findAllPrixodSummaByYear(year));
    }

    public ApiResponse<List<PrixodSummaByCreatedBy>> prixodSummaByCreatedByYear(int year){
        return ApiResponse.ok(prixodTovarRepository.findAllPrixodSummaByCreatedByByYear(year));
    }

    public ApiResponse<List<RasxodSumma>> rasxodSummaByYear(int year){
        return ApiResponse.ok(rasxodTovarRepository.findAllRasxodSummaByYear(year));
    }

    public ApiResponse<List<RasxodSummaByCreatedBy>> rasxodSummaByCreatedByYear(int year){
        return ApiResponse.ok(rasxodTovarRepository.findAllRasxodSummaByCreatedByByYear(year));
    }

    public ApiResponse<List<VozvratSumma>> vozvratSummaByYear(int year){
        return ApiResponse.ok(vozvratTovarRepository.findAllVozvratSummaByYear(year));
    }

    public ApiResponse<List<VozvratSummaByCreatedBy>> vozvratSummaByCreatedByYear(int year){
        return ApiResponse.ok(vozvratTovarRepository.findAllVozvratSummaByCreatedByByYear(year));
    }

    public ApiResponse<?> home(String type, int year, Model model){
        return switch (type){
            case HomeController._prixodSumma -> {
                ApiResponse<List<PrixodSumma>> response = prixodSummaByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(PrixodSumma::getSum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            case HomeController._rasxodSumma -> {
                ApiResponse<List<RasxodSumma>> response = rasxodSummaByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(RasxodSumma::getSum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            case HomeController._vozvratSumma -> {
                ApiResponse<List<VozvratSumma>> response = vozvratSummaByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(VozvratSumma::getSum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            case HomeController._prixodSummaByCreated -> {
                ApiResponse<List<PrixodSummaByCreatedBy>> response = prixodSummaByCreatedByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(PrixodSummaByCreatedBy::getSum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            case HomeController._rasxodSummaByCreated -> {
                ApiResponse<List<RasxodSummaByCreatedBy>> response = rasxodSummaByCreatedByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(RasxodSummaByCreatedBy::getSum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            case HomeController._vozvratSummaByCreated -> {
                ApiResponse<List<VozvratSummaByCreatedBy>> response = vozvratSummaByCreatedByYear(year);
                model.addAttribute(HomeController._total, response.getData().stream()
                        .map(VozvratSummaByCreatedBy::getSum)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
                yield response;
            }
            default -> throw new ApiException("");
        };
    }
}
