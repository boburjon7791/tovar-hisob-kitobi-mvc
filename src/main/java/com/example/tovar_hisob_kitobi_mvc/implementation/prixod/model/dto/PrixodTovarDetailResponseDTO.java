package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PrixodTovarDetailResponseDTO(
        UUID id,
        UUID prixodTovarId,
        Long tovarId,
        String tovarNomi,
        String tovarRasmi,
        BigDecimal miqdori,
        BigDecimal summasi,
        String shtrixKod
) {
}
