package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RasxodTovarResponseDTO(
        UUID id,
        String createdAt,
        String updatedAt,
        Long createdBy,
        Long updatedBy,
        String createdByFullName,
        String updatedByFullName,
        BigDecimal totalSumma,
        List<RasxodTovarDetailResponseDTO> details,
        boolean tasdiqlandi
) {
}
