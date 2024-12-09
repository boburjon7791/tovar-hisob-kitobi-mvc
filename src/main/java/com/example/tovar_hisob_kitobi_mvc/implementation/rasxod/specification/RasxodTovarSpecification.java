package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.specification;

import com.example.tovar_hisob_kitobi_mvc.base.specification.BaseSpecification;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.filtering.RasxodTovarFiltering;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class RasxodTovarSpecification implements BaseSpecification<RasxodTovar, RasxodTovarFiltering> {
    @Override
    public Specification<RasxodTovar> specification(RasxodTovarFiltering request) {
        Specification<RasxodTovar> specification = empty();
        if (request.getFromDate()!=null) {
            specification=specification.and(fromCreatedAt(request.getFromDate()));
        }
        if (request.getToDate()!=null) {
            specification=specification.and(toCreatedAt(request.getToDate()));
        }
        return specification;
    }
}
