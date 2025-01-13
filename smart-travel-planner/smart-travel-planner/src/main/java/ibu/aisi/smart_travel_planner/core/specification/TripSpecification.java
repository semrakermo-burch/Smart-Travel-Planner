package ibu.aisi.smart_travel_planner.core.specification;

import ibu.aisi.smart_travel_planner.core.model.Trip;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

import ibu.aisi.smart_travel_planner.core.model.Trip;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class TripSpecification {

    public static Specification<Trip> hasName(String name) {
        return (root, query, builder) ->
                name == null || name.trim().isEmpty() ? null : builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Trip> hasDescription(String description) {
        return (root, query, builder) ->
                description == null || description.trim().isEmpty() ? null : builder.like(builder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }

    public static Specification<Trip> hasStartDate(Date startDate) {
        return (root, query, builder) ->
                startDate == null ? null : builder.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    public static Specification<Trip> hasEndDate(Date endDate) {
        return (root, query, builder) ->
                endDate == null ? null : builder.lessThanOrEqualTo(root.get("endDate"), endDate);
    }

    public static Specification<Trip> hasStatus(String status) {
        return (root, query, builder) ->
                status == null || status.trim().isEmpty() ? null : builder.equal(root.get("status"), status);
    }

    public static Specification<Trip> hasCityId(Long cityId) {
        return (root, query, builder) ->
                cityId == null ? null : builder.equal(root.get("city").get("id"), cityId);
    }

    public static Specification<Trip> hasUserEmail(String email) {
        return (root, query, builder) ->
                email == null || email.trim().isEmpty() ? null : builder.equal(root.get("user").get("email"), email);
    }
}


