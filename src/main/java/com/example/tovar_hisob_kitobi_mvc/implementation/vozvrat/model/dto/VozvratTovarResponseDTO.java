package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record VozvratTovarResponseDTO(
        UUID id,
        String createdAt,
        String updatedAt,
        Long createdBy,
        Long updatedBy,
        String createdByFullName,
        String updatedByFullName,
        BigDecimal totalSumma,
        List<VozvratTovarDetailResponseDTO> details,
        boolean tasdiqlandi,
        UUID rasxodTovarId,
        String izoh
) {
}
