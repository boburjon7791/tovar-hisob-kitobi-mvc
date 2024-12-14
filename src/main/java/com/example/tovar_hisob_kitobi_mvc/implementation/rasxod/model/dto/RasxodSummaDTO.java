package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto;

import com.example.tovar_hisob_kitobi_mvc.base.common.internationalization.Localization;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSumma;

import java.math.BigDecimal;
import java.util.Locale;

public record RasxodSummaDTO(int year, String month, BigDecimal sum) {
    public static RasxodSummaDTO of(RasxodSumma rasxodSumma, Localization localization){
        return new RasxodSummaDTO(rasxodSumma.getYear(), localization.getMessage("m_"+rasxodSumma.getMonth()), rasxodSumma.getSum());
    }
    public static RasxodSummaDTO of(RasxodSumma rasxodSumma, Localization localization, Locale locale){
        return new RasxodSummaDTO(rasxodSumma.getYear(), localization.getMessage("m_"+rasxodSumma.getMonth(), locale), rasxodSumma.getSum());
    }
}
