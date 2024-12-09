package com.example.tovar_hisob_kitobi_mvc.base.model.mapper;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntity;
import org.mapstruct.Mapping;

public interface BaseMapper<ENTITY, REQUEST_DTO, RESPONSE_DTO> {
    @Mapping(target = BaseEntity._id, ignore = true)
    @Mapping(target = BaseEntity._createdAt, ignore = true)
    @Mapping(target = BaseEntity._createdBy, ignore = true)
    @Mapping(target = BaseEntity._updatedAt, ignore = true)
    @Mapping(target = BaseEntity._updatedBy, ignore = true)
    @Mapping(target = BaseEntity._deleted, ignore = true)
    ENTITY toEntity(REQUEST_DTO dto);
    RESPONSE_DTO toDto(ENTITY entity);
    void update(ENTITY entity, REQUEST_DTO dto);
}
