package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.specification;

import com.example.tovar_hisob_kitobi_mvc.base.specification.BaseSpecification;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.filtering.PrixodTovarFiltering;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PrixodTovarSpecification implements BaseSpecification<PrixodTovar, PrixodTovarFiltering> {
    @Override
    public Specification<PrixodTovar> specification(PrixodTovarFiltering request) {
        Specification<PrixodTovar> specification = empty();
        if (request.getFromDate()!=null) {
            specification=specification.and(fromCreatedAt(request.getFromDate()));
        }
        if (request.getToDate()!=null) {
            specification=specification.and(toCreatedAt(request.getToDate()));
        }
        return specification;
    }
}
