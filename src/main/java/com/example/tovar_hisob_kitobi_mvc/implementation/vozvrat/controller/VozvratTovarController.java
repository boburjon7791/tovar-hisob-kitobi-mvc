package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.controller;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.controller.BaseControllerMVC;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.filtering.VozvratTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.service.VozvratTovarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping(VozvratTovarController._apiPrefix)
@RequiredArgsConstructor
public class VozvratTovarController extends BaseControllerMVC<VozvratTovar, UUID, VozvratTovarRequestDTO, VozvratTovarResponseDTO, VozvratTovarFiltering> {
    public static final String _apiPrefix="/vozvrat-tovar";
    private final VozvratTovarService vozvratTovarService;
    @Override
    public String apiPrefix() {
        return _apiPrefix;
    }

    @Override
    public UUID getId(VozvratTovarResponseDTO vozvratTovarResponseDTO) {
        return vozvratTovarResponseDTO.vozvratTovarId();
    }

    @PostMapping("/start")
    public String start(Model model){
        UUID id = vozvratTovarService.start();
        model.addAttribute(_response, id);
        return apiPrefix()+"/start";
    }

    @PostMapping("/end")
    public String end(Model model, @RequestParam UUID id){
        ApiResponse<VozvratTovarResponseDTO> response = vozvratTovarService.end(id);
        model.addAttribute(_response, response);
        return apiPrefix()+"/about";
    }
}
