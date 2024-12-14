package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto;

import com.example.tovar_hisob_kitobi_mvc.base.common.internationalization.Localization;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.projection.VozvratSummaByCreatedBy;

import java.math.BigDecimal;
import java.util.Locale;

public record VozvratSummaByCreatedByDTO(int year, String month, BigDecimal sum, long createdBy, String fullName) {
    public static VozvratSummaByCreatedByDTO of(VozvratSummaByCreatedBy vozvratSumma, Localization localization){
        return new VozvratSummaByCreatedByDTO(vozvratSumma.getYear(), localization.getMessage("m_"+vozvratSumma.getMonth()), vozvratSumma.getSum(), vozvratSumma.getCreatedBy(), vozvratSumma.getFullName());
    }
    public static VozvratSummaByCreatedByDTO of(VozvratSummaByCreatedBy vozvratSumma, Localization localization, Locale locale){
        return new VozvratSummaByCreatedByDTO(vozvratSumma.getYear(), localization.getMessage("m_"+vozvratSumma.getMonth(), locale), vozvratSumma.getSum(), vozvratSumma.getCreatedBy(), vozvratSumma.getFullName());
    }
}
