package com.example.tovar_hisob_kitobi_mvc.implementation.tovar.controller;

import com.example.tovar_hisob_kitobi_mvc.base.controller.BaseControllerMVC;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.dto.TovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.dto.TovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.filtering.TovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(TovarController._apiPrefix)
@RequiredArgsConstructor
public class TovarController extends BaseControllerMVC<Tovar, Long, TovarRequestDTO, TovarResponseDTO, TovarFiltering> {
    public static final String _apiPrefix="tovar";
    private final TovarService tovarService;
    @Override
    public String apiPrefix() {
        return _apiPrefix;
    }

    @Override
    public Long getId(TovarResponseDTO tovarResponseDTO) {
        return tovarResponseDTO.id();
    }

    @GetMapping(value = "/image/{image}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public byte[] image(@PathVariable String image){
        return tovarService.getImage(image);
    }

    @Override
    public String deleteById(Long id) {
        throw new RuntimeException();
    }
}
