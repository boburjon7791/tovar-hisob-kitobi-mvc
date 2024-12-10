package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.specification;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntity;
import com.example.tovar_hisob_kitobi_mvc.base.specification.BaseSpecification;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.filtering.RasxodTovarFiltering;
import jakarta.persistence.criteria.Predicate;
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
        if (request.getUserId()!=null) {
            specification=specification.and(user(request.getUserId()));
        }
        return specification;
    }
    public Specification<RasxodTovar> user(Long userId){
        return (root, query, criteriaBuilder) -> {
            Predicate createdBy = criteriaBuilder.equal(root.get(BaseEntity._createdBy), userId);
            Predicate updatedBy = criteriaBuilder.equal(root.get(BaseEntity._updatedBy), userId);
            return criteriaBuilder.or(createdBy, updatedBy);
        };
    }
}
