package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.projection;

import java.math.BigDecimal;

public interface VozvratSummaByCreatedBy {
    BigDecimal getSum();
    Integer getMonth();
    Integer getYear();
    Long getCreatedBy();
    String getFullName();
}
