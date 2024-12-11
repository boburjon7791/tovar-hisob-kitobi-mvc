package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection;

import java.math.BigDecimal;

public interface RasxodSummaByCreatedBy {
    BigDecimal getSum();
    Integer getMonth();
    Integer getYear();
    Long getCreatedBy();
    String getFullName();
}
