package com.example.tovar_hisob_kitobi_mvc.implementation.user.specification;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntity;
import com.example.tovar_hisob_kitobi_mvc.base.specification.BaseSpecification;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.enums.Lavozim;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.filtering.UserFiltering;
import jakarta.persistence.criteria.Predicate;
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
        if (request.getSearch()!=null && !request.getSearch().isBlank()) {
            specification=specification.and(search(request.getSearch()));
        }
        if (request.getLavozim()!=null) {
            specification=specification.and(lavozim(request.getLavozim()));
        }
        return specification;
    }

    public Specification<User> lavozim(Lavozim lavozim){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Lavozim._lavozim), lavozim);
    }

    public Specification<User> search(String search){
        String text=likeExpression(search);
        return (root, query, criteriaBuilder) -> {
            Predicate ism=criteriaBuilder.like(criteriaBuilder.lower(root.get(User._ism)),text);
            Predicate id=null;
            try {
                id=criteriaBuilder.equal(root.get(BaseEntity._id), Long.valueOf(text));
            }catch (Exception ignore){}

            Predicate familya=criteriaBuilder.like(criteriaBuilder.lower(root.get(User._familya)),text);
            Predicate login=criteriaBuilder.like(criteriaBuilder.lower(root.get(User._login)),text);
            Predicate telefonRaqam=criteriaBuilder.like(criteriaBuilder.lower(root.get(User._telefonRaqam)),text);

            return id!=null? criteriaBuilder.or(ism, familya, login, telefonRaqam, id):criteriaBuilder.or(ism, familya, login, telefonRaqam);
        };
    }
}
