package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection;

import java.math.BigDecimal;

public interface PrixodSummaByCreatedBy {
    BigDecimal getSum();
    Integer getMonth();
    Integer getYear();
    Long getCreatedBy();
    String getFullName();
}
