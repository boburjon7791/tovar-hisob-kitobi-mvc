package com.example.tovar_hisob_kitobi_mvc.base.model.filtering;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class BaseFiltering {
    private Integer page=0;
    private Integer size=10;
    private String search;
    private LocalDate fromDate;
    private LocalDate toDate;
    private boolean all;
}
