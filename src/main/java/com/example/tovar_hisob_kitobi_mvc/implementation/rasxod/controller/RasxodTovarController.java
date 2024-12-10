package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.controller;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.controller.BaseControllerMVC;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.filtering.RasxodTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.service.RasxodTovarService;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping(RasxodTovarController._apiPrefix)
@RequiredArgsConstructor
public class RasxodTovarController extends BaseControllerMVC<RasxodTovar, UUID, RasxodTovarRequestDTO, RasxodTovarResponseDTO, RasxodTovarFiltering> {
    public static final String _apiPrefix="rasxod-tovar";
    private final RasxodTovarService rasxodTovarService;
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
    public UUID getId(RasxodTovarResponseDTO rasxodTovarResponseDTO) {
        return rasxodTovarResponseDTO.id();
    }

    @GetMapping("/start")
    public String start(Model model){
        UUID id = rasxodTovarService.start();
        model.addAttribute(_response, id);
        return redirectForm(id);
    }

    @GetMapping("/form/{id}")
    public String form(@PathVariable UUID id, Model model){
        ApiResponse<RasxodTovarResponseDTO> response = rasxodTovarService.findById(id);
        model.addAttribute(_response, response);
        return apiPrefix()+"/form";
    }

    @Override
    @PostMapping("/create")
    public String create(@ModelAttribute RasxodTovarRequestDTO requestDTO) {
        super.create(requestDTO);
        return redirectForm(requestDTO.rasxodTovarId());
    }

    @PostMapping("/end")
    public String end(@RequestParam UUID id){
        rasxodTovarService.end(id);
        return redirectList();
    }

    @PostMapping("/delete-detail")
    public String deleteDetail(@RequestParam UUID detailId, @RequestParam UUID rasxodTovarId){
        rasxodTovarService.deleteDetail(detailId, rasxodTovarId);
        return redirectForm(rasxodTovarId);
    }

    @Override
    @GetMapping("/list")
    public String findAll(RasxodTovarFiltering request, Model model) {
        String response = super.findAll(request, model);
        addUsers(model, userService);
        return response;
    }
}
