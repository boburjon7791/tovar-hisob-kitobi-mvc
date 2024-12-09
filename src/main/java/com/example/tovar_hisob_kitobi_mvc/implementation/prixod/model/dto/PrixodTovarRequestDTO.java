package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record PrixodTovarRequestDTO(
        @NotNull
        UUID prixodTovarId,

        UUID prixodTovarDetailId,

        Long tovarId,

        String shtrixKod,

        @Positive
        BigDecimal miqdori,

        @PositiveOrZero
        BigDecimal summasi,

        String izoh
) {
}
