package com.example.tovar_hisob_kitobi_mvc.base.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.stream.IntStream;

@Getter
@AllArgsConstructor
public class PaginationResponse {
    int page;
    int size;
    long total;
    int current;
    int left;
    int right;
    int[] pages;
    public static PaginationResponse of(Page<?> page){
        return new PaginationResponse(page.getTotalPages(), page.getSize(), page.getTotalElements(), page.getNumber(), page.getTotalPages()==0? 0:page.getNumber()==0 ? 0:page.getNumber()-1, page.getTotalPages()==0? 0:page.getTotalPages()-1==page.getNumber()? page.getNumber():page.getNumber()+1, IntStream.range(0, page.getTotalPages()).toArray());
    }
}
