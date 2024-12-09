package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record VozvratTovarResponseDTO(
        UUID vozvratTovarId,
        BigDecimal totalSumma,
        Set<VozvratTovarDetailResponseDTO> details
) {
}
