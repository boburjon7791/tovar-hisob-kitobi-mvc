package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto;

import com.example.tovar_hisob_kitobi_mvc.base.common.internationalization.Localization;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSummaByCreatedBy;
import java.math.BigDecimal;
import java.util.Locale;

public record PrixodSummaByCreatedByDTO(int year, String month, BigDecimal sum, long createdBy, String fullName) {
    public static PrixodSummaByCreatedByDTO of(PrixodSummaByCreatedBy prixodSumma, Localization localization){
        return new PrixodSummaByCreatedByDTO(prixodSumma.getYear(), localization.getMessage("m_"+prixodSumma.getMonth()), prixodSumma.getSum(), prixodSumma.getCreatedBy(), prixodSumma.getFullName());
    }
    public static PrixodSummaByCreatedByDTO of(PrixodSummaByCreatedBy prixodSumma, Localization localization, Locale locale){
        return new PrixodSummaByCreatedByDTO(prixodSumma.getYear(), localization.getMessage("m_"+prixodSumma.getMonth(), locale), prixodSumma.getSum(), prixodSumma.getCreatedBy(), prixodSumma.getFullName());
    }
}
