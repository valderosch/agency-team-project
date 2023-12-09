package com.team.travel.dao;

import com.team.travel.dto.tour.TourResponse;
import com.team.travel.entity.TourStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public class TourFilterDAO {


    private final EntityManager em;
    public TourFilterDAO(EntityManager em) {
        this.em = em;
    }


    public List<TourResponse> findAllTourByQuery(
            int priceStart,
            int priceEnd,
            TourStatus status,
            LocalDate startDate,
            LocalDate endDate) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<TourResponse> criteriaQuery = criteriaBuilder.createQuery(TourResponse.class);

        // аналогічно select * from
        Root<TourResponse> root = criteriaQuery.from(TourResponse.class);

        // where вираз
        Predicate pricePredicateStart = criteriaBuilder
                .greaterThan(root.get("price"),priceStart);

        Predicate pricePredicateEnd = criteriaBuilder
                .lessThan(root.get("price"),priceEnd);

        Predicate statusPredicate = criteriaBuilder
                .equal(root.get("status"),status);

        Predicate startDatePredicate = criteriaBuilder
                .equal(root.get("startDate"),startDate);

        Predicate endDatePredicate = criteriaBuilder
                .equal(root.get("endDate"),endDate);

        // аналогічно параметру AND В SQL

        Predicate andPredicate = criteriaBuilder.and(
                pricePredicateStart,
                pricePredicateEnd,
                statusPredicate,
                startDatePredicate,
                endDatePredicate
        );
        //SELECT * FROM tour WHERE price > priceStart AND price < priceEnd
        // AND status = status AND startDate = startDate AND endDate = endDate
        // Формуємо готовий запит зі сформованим предикатом andPredicate
        criteriaQuery.where(andPredicate);

        //Фінальне формування запиту та  його повернення
        TypedQuery<TourResponse> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
