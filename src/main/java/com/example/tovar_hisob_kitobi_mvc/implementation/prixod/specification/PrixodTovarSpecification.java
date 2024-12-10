package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.specification;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntity;
import com.example.tovar_hisob_kitobi_mvc.base.specification.BaseSpecification;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.filtering.PrixodTovarFiltering;
import jakarta.persistence.criteria.Predicate;
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
        if (request.getUserId()!=null) {
            specification=specification.and(user(request.getUserId()));
        }
        return specification;
    }

    public Specification<PrixodTovar> user(Long userId){
        return (root, query, criteriaBuilder) -> {
            Predicate createdBy = criteriaBuilder.equal(root.get(BaseEntity._createdBy), userId);
            Predicate updatedBy = criteriaBuilder.equal(root.get(BaseEntity._updatedBy), userId);
            return criteriaBuilder.or(createdBy, updatedBy);
        };
    }
}
