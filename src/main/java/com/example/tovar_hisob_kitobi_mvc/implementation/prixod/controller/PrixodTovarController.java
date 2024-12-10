package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.controller;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.controller.BaseControllerMVC;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.filtering.PrixodTovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.service.PrixodTovarService;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping(PrixodTovarController._apiPrefix)
@RequiredArgsConstructor
public class PrixodTovarController extends BaseControllerMVC<PrixodTovar, UUID, PrixodTovarRequestDTO, PrixodTovarResponseDTO, PrixodTovarFiltering> {
    public static final String _apiPrefix="prixod-tovar";
    private final PrixodTovarService prixodTovarService;
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
    public UUID getId(PrixodTovarResponseDTO prixodTovarResponseDTO) {
        return prixodTovarResponseDTO.id();
    }

    @GetMapping("/start")
    public String start(){
        UUID id = prixodTovarService.start();
        return redirectForm(id);
    }

    @GetMapping("/form/{id}")
    public String form(@PathVariable UUID id, Model model){
        ApiResponse<PrixodTovarResponseDTO> response = prixodTovarService.findById(id);
        model.addAttribute(_response, response);
        return apiPrefix()+"/form";
    }

    @Override
    @PostMapping("/create")
    public String create(@ModelAttribute PrixodTovarRequestDTO requestDTO) {
        super.create(requestDTO);
        return redirectForm(requestDTO.prixodTovarId());
    }



    @PostMapping("/end")
    public String end(@RequestParam UUID id){
        prixodTovarService.end(id);
        return redirectList();
    }

    @PostMapping("/delete-detail")
    public String deleteDetail(@RequestParam UUID detailId, @RequestParam UUID prixodTovarId){
        prixodTovarService.deleteDetail(detailId, prixodTovarId);
        return redirectForm(prixodTovarId);
    }

    @Override
    @GetMapping("/list")
    public String findAll(PrixodTovarFiltering request, Model model) {
        String response = super.findAll(request, model);
        addUsers(model, userService);
        return response;
    }
}
