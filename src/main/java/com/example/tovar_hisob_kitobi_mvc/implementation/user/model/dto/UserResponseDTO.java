package com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto;

import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.enums.Lavozim;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long createdById,
        Long updatedById,
        String creaetdByFullName,
        String updatedByFullName,
        String ism,
        String familya,
        String login,
        String telefonRaqam,
        Lavozim lavozim
) {
}
