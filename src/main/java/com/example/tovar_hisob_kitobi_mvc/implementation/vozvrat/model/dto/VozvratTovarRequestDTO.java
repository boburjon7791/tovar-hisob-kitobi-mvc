package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record VozvratTovarRequestDTO(
        @NotNull
        UUID vozvratTovarId,

        @NotNull
        Long tovarId,

        @Positive
        BigDecimal miqdori,

        @PositiveOrZero
        BigDecimal summasi
) {
}
