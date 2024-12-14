package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto;

import com.example.tovar_hisob_kitobi_mvc.base.common.internationalization.Localization;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSummaByCreatedBy;

import java.math.BigDecimal;
import java.util.Locale;

public record RasxodSummaByCreatedByDTO(int year, String month, BigDecimal sum, long createdBy, String fullName) {
    public static RasxodSummaByCreatedByDTO of(RasxodSummaByCreatedBy prixodSumma, Localization localization){
        return new RasxodSummaByCreatedByDTO(prixodSumma.getYear(), localization.getMessage("m_"+prixodSumma.getMonth()), prixodSumma.getSum(), prixodSumma.getCreatedBy(), prixodSumma.getFullName());
    }
    public static RasxodSummaByCreatedByDTO of(RasxodSummaByCreatedBy prixodSumma, Localization localization, Locale locale){
        return new RasxodSummaByCreatedByDTO(prixodSumma.getYear(), localization.getMessage("m_"+prixodSumma.getMonth(), locale), prixodSumma.getSum(), prixodSumma.getCreatedBy(), prixodSumma.getFullName());
    }
}
