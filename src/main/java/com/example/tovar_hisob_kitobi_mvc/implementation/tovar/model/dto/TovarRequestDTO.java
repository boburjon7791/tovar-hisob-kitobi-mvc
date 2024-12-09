package com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record TovarRequestDTO(
        Long id,

        @NotBlank
        String nomi,

        MultipartFile file,

        String izoh,

        BigDecimal prixodSumma,

        BigDecimal rasxodSumma,

        String shtrixKod,

        String olchovBirligi
) {
        public TovarRequestDTO{
                olchovBirligi=olchovBirligi.toUpperCase();
        }
}
