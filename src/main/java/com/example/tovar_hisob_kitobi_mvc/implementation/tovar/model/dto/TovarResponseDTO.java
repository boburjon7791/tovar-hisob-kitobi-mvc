package com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.dto;

import java.math.BigDecimal;

public record TovarResponseDTO(
        Long id,
        String createdAt,
        String updatedAt,
        Long createdById,
        Long updatedById,
        String creaetdByFullName,
        String updatedByFullName,
        BigDecimal ostatka,
        String rasm,
        BigDecimal prixodSumma,
        BigDecimal rasxodSumma,
        String nomi,
        String shtrixKod,
        String olchovBirligi,
        String izoh
) {
}
