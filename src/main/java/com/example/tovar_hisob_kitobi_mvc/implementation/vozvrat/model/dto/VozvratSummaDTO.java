package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto;

import com.example.tovar_hisob_kitobi_mvc.base.common.internationalization.Localization;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.projection.VozvratSumma;

import java.math.BigDecimal;
import java.util.Locale;

public record VozvratSummaDTO(int year, String month, BigDecimal sum) {
    public static VozvratSummaDTO of(VozvratSumma vozvratSumma, Localization localization){
        return new VozvratSummaDTO(vozvratSumma.getYear(), localization.getMessage("m_"+vozvratSumma.getMonth()), vozvratSumma.getSum());
    }
    public static VozvratSummaDTO of(VozvratSumma vozvratSumma, Localization localization, Locale locale){
        return new VozvratSummaDTO(vozvratSumma.getYear(), localization.getMessage("m_"+vozvratSumma.getMonth(), locale), vozvratSumma.getSum());
    }
}
