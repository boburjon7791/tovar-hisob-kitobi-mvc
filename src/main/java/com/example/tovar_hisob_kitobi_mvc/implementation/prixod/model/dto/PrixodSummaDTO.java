package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto;

import com.example.tovar_hisob_kitobi_mvc.base.common.internationalization.Localization;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSumma;

import java.math.BigDecimal;

public record PrixodSummaDTO(int year, String month, BigDecimal sum) {
    public static PrixodSummaDTO of(PrixodSumma rasxodSumma, Localization localization){
        return new PrixodSummaDTO(rasxodSumma.getYear(), localization.getMessage("m_"+rasxodSumma.getMonth()), rasxodSumma.getSum());
    }
}
