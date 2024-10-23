package com.laberit.sina.bootcamp.extra.awesomefinalproject.specification;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserSpecification {
    public static Specification<User> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(criteriaBuilder.lower(root.get("name")), name.toLowerCase());
    }

    public static Specification<User> hasSurnames(String surnames) {
        return (root, query, criteriaBuilder) ->
                surnames == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(criteriaBuilder.lower(root.get("surnames")), surnames.toLowerCase());
    }

    public static Specification<User> hasRole(String role) {
        return (root, query, criteriaBuilder) ->
                role == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(criteriaBuilder.lower(root.get("role").get("name")), role.toLowerCase());
    }

    public static Specification<User> hasPermissions(List<String> permissions) {
        return (root, query, criteriaBuilder) -> {
            if (permissions == null || permissions.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            List<jakarta.persistence.criteria.Predicate> predicates = permissions.stream()
                    .map(permission -> criteriaBuilder.isMember(permission.toLowerCase(), root.get("role").get("permissions")))
                    .toList();
            return criteriaBuilder.or(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }

    public static Specification<User> hasUsername(String username) {
        return (root, query, criteriaBuilder) ->
                username == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(criteriaBuilder.lower(root.get("username")), username.toLowerCase());
    }
}