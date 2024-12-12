package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.controller;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.controller.BaseControllerMVC;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.service.UserService;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.filtering.VozvratTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.service.VozvratTovarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping(VozvratTovarController._apiPrefix)
@RequiredArgsConstructor
public class VozvratTovarController extends BaseControllerMVC<VozvratTovar, UUID, VozvratTovarRequestDTO, VozvratTovarResponseDTO, VozvratTovarFiltering> {
    public static final String _apiPrefix="vozvrat-tovar";
    private final VozvratTovarService vozvratTovarService;
    private final UserService userService;
    @Override
    public String apiPrefix() {
        return _apiPrefix;
    }

    public String redirectForm(UUID id){
        return "redirect:/"+apiPrefix()+"/form/"+id;
    }

    public String redirectAbout(UUID id){
        return "redirect:/"+apiPrefix()+"/about/"+id;
    }

    @Override
    public UUID getId(VozvratTovarResponseDTO vozvratTovarResponseDTO) {
        return vozvratTovarResponseDTO.id();
    }

    @GetMapping("/start/{rasxodTovarId}")
    public String start(Model model, @PathVariable UUID rasxodTovarId){
        UUID id = vozvratTovarService.start(rasxodTovarId);
        model.addAttribute(_response, id);
        return redirectForm(id);
    }

    @GetMapping("/form/{id}")
    public String form(@PathVariable UUID id, Model model){
        ApiResponse<VozvratTovarResponseDTO> response = vozvratTovarService.findById(id);
        model.addAttribute(_response, response);
        return apiPrefix()+"/form";
    }

    @Override
    @PostMapping("/create")
    public String create(@ModelAttribute VozvratTovarRequestDTO requestDTO) {
        super.create(requestDTO);
        return redirectForm(requestDTO.vozvratTovarId());
    }

    @PostMapping("/end")
    public String end(@RequestParam UUID id){
        vozvratTovarService.end(id);
        return redirectList();
    }

    @PostMapping("/delete-detail")
    public String deleteDetail(@RequestParam UUID detailId, @RequestParam UUID vozvratTovarId){
        vozvratTovarService.deleteDetail(detailId, vozvratTovarId);
        return redirectForm(vozvratTovarId);
    }

    @Override
    @GetMapping("/list")
    public String findAll(VozvratTovarFiltering request, Model model) {
        String response = super.findAll(request, model);
        addUsers(model, userService);
        return response;
    }

    @Override
    public String deleteById(UUID id) {
        throw new RuntimeException();
    }
}
