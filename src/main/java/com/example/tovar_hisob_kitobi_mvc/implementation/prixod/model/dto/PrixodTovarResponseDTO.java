package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PrixodTovarResponseDTO(
        UUID id,
        String createdAt,
        String updatedAt,
        Long createdBy,
        Long updatedBy,
        String createdByFullName,
        String updatedByFullName,
        BigDecimal totalSumma,
        List<PrixodTovarDetailResponseDTO> details,
        boolean tasdiqlandi
) {
}
