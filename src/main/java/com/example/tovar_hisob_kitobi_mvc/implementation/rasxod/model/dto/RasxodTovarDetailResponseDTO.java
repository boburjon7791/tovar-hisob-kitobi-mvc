package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record RasxodTovarDetailResponseDTO(
        UUID id,
        UUID rasxodTovarId,
        Long tovarId,
        String tovarNomi,
        String tovarRasmi,
        BigDecimal miqdori,
        BigDecimal summasi,
        String shtrixKod
) {
}
