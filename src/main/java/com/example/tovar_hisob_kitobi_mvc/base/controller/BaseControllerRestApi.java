package com.example.tovar_hisob_kitobi_mvc.base.controller;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public class BaseControllerRestApi<ENTITY, ID, REQUEST_DTO, RESPONSE_DTO, FILTERING> {
    private BaseService<ENTITY,ID, REQUEST_DTO, RESPONSE_DTO, FILTERING> baseService;

    @Autowired
    public void setBaseService(@Lazy BaseService<ENTITY, ID, REQUEST_DTO, RESPONSE_DTO, FILTERING> baseService) {
        this.baseService = baseService;
    }

    @PostMapping
    public ApiResponse<RESPONSE_DTO> create(@RequestBody REQUEST_DTO dto){
        return baseService.create(dto);
    }

    @PutMapping("/{id}")
    public ApiResponse<RESPONSE_DTO> update(@RequestBody REQUEST_DTO dto, @PathVariable ID id){
        return baseService.update(dto, id);
    }

    @GetMapping("/{id}")
    public ApiResponse<RESPONSE_DTO> findById(@PathVariable ID id){
        return baseService.findById(id);
    }

    @GetMapping
    public ApiResponse<List<RESPONSE_DTO>> findAll(@ModelAttribute FILTERING request){
        return baseService.findAll(request);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteById(@PathVariable ID id){
        return baseService.deleteById(id);
    }
}
