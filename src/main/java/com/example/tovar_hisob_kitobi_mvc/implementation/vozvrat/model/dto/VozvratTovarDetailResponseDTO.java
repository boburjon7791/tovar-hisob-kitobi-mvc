package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record VozvratTovarDetailResponseDTO(
        UUID vozvratTovarId,
        Long tovarId,
        String tovarNomi,
        String tovarRasmi,
        BigDecimal miqdori,
        BigDecimal summasi
) {
}
