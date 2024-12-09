package com.example.tovar_hisob_kitobi_mvc.implementation.tovar.specification;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntity;
import com.example.tovar_hisob_kitobi_mvc.base.specification.BaseSpecification;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.filtering.TovarFiltering;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TovarSpecification implements BaseSpecification<Tovar, TovarFiltering> {
    @Override
    public Specification<Tovar> specification(TovarFiltering request) {
        Specification<Tovar> specification = empty();
        if (request.getFromDate()!=null) {
            specification=specification.and(fromCreatedAt(request.getFromDate()));
        }
        if (request.getToDate()!=null) {
            specification=specification.and(toCreatedAt(request.getToDate()));
        }
        if (request.getSearch()!=null && !request.getSearch().isBlank()) {
            specification=specification.and(search(request.getSearch()));
        }
        return specification;
    }

    public Specification<Tovar> search(String text){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Tovar._nomi)), likeExpression(text));
    }
}
