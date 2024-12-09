package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.specification;

import com.example.tovar_hisob_kitobi_mvc.base.specification.BaseSpecification;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.filtering.VozvratTovarFiltering;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class VozvratTovarSpecification implements BaseSpecification<VozvratTovar, VozvratTovarFiltering> {
    @Override
    public Specification<VozvratTovar> specification(VozvratTovarFiltering request) {
        Specification<VozvratTovar> specification = empty();
        if (request.getFromDate()!=null) {
            specification=specification.and(fromCreatedAt(request.getFromDate()));
        }
        if (request.getToDate()!=null) {
            specification=specification.and(toCreatedAt(request.getToDate()));
        }
        return specification;
    }
}
