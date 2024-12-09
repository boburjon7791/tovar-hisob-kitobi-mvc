package com.example.tovar_hisob_kitobi_mvc.implementation.user.specification;

import com.example.tovar_hisob_kitobi_mvc.base.specification.BaseSpecification;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.filtering.UserFiltering;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecification implements BaseSpecification<User, UserFiltering> {
    @Override
    public Specification<User> specification(UserFiltering request) {
        Specification<User> specification = empty();
        if (request.getFromDate()!=null) {
            specification=specification.and(fromCreatedAt(request.getFromDate()));
        }
        if (request.getToDate()!=null) {
            specification=specification.and(toCreatedAt(request.getToDate()));
        }
        return specification;
    }
}
