package com.example.tovar_hisob_kitobi_mvc.base.specification;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntity;
import com.example.tovar_hisob_kitobi_mvc.base.model.filtering.BaseFiltering;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface BaseSpecification<ENTITY, FILTERING> {
    Specification<ENTITY> specification(FILTERING request);

    default Specification<ENTITY> fromCreatedAt(LocalDate fromCreatedDate){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.function("date",LocalDate.class, root.get(BaseEntity._createdAt)), fromCreatedDate);
    }

    default Specification<ENTITY> toCreatedAt(LocalDate toCreatedDate){
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.function("date",LocalDate.class,root.get(BaseEntity._createdAt)), toCreatedDate);
    }

    default Pageable pageable(FILTERING request) {
        BaseFiltering requestModel = (BaseFiltering)request;
        return PageRequest.of(requestModel.getPage(), requestModel.getSize(), Sort.by(Sort.Direction.DESC, BaseEntity._createdAt));
    }

    default String likeExpression(String text){
        return "%"+text.toLowerCase()+"%";
    }

    default Specification<ENTITY> empty(){
        return Specification.where(null);
    }
}
