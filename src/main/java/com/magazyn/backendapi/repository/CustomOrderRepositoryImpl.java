package com.magazyn.backendapi.repository;

import com.magazyn.backendapi.dto.FilterOrderDTO;
import com.magazyn.backendapi.entity.Order;
import com.magazyn.backendapi.entity.OrderStatus;
import com.magazyn.backendapi.entity.ShipmentDimensions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomOrderRepositoryImpl implements CustomOrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> orderFilter(FilterOrderDTO filter) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Order> query = cb.createQuery(Order.class);
//        Root<Order> orderRoot = query.from(Order.class);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        // Filter by trackingNumber
//        if (filter.getFilterText() != null && !filter.getFilterText().matches(".*[a-zA-Z]+.*")) {
//            predicates.add(cb.like(orderRoot.get("trackingNumber"), "%" + filter.getFilterText() + "%"));
//        }
//
//        // Filter by date
//        if (filter.getFilterData() != null) {
//            predicates.add(cb.equal(orderRoot.get("creationDate"), filter.getFilterData()));
//        }
//
//        // Filter by status
//        if (filter.getFilterStatus() != null && !"WSZYSTKIE".equals(filter.getFilterStatus())) {
//            OrderStatus status = OrderStatus.valueOf(filter.getFilterStatus());
//            predicates.add(cb.equal(orderRoot.get("status"), status));
//        }
//
//        // Filter by kind
//        if (filter.getFilterKindEur() == ShipmentDimensions.EUR && filter.getFilterKindHp() != ShipmentDimensions.HP) {
//            predicates.add(cb.equal(orderRoot.get("dimensions"), ShipmentDimensions.EUR));
//        } else if (filter.getFilterKindEur() != ShipmentDimensions.EUR && filter.getFilterKindHp() == ShipmentDimensions.HP) {
//            predicates.add(cb.equal(orderRoot.get("dimensions"), ShipmentDimensions.HP));
//        }
//
//        query.where(predicates.toArray(new Predicate[0]));
//
//        // Sortowanie
//        Expression<Object> orderByCase = cb.selectCase()
//                .when(cb.equal(orderRoot.get("status"), OrderStatus.MAGAZYN), 1)
//                .when(cb.equal(orderRoot.get("status"), OrderStatus.DOSTAWA), 2)
//                .when(cb.equal(orderRoot.get("status"), OrderStatus.DOSTARCZONO), 3)
//                .otherwise(4);
//
//        query.orderBy(cb.asc(orderByCase));
//
//        return entityManager.createQuery(query).getResultList();
        return null;
    }

    @Override
    public Page<Order> orderFilterWithPagination(FilterOrderDTO filter, Pageable pageable) {
        System.out.println("Pageable Page: " + pageable.getPageNumber());
        System.out.println("Pageable Size: " + pageable.getPageSize());
        System.out.println("KindEur: " + filter.getFilterKindEur());
        System.out.println("KindHp: " + filter.getFilterKindHp());
        System.out.println("Status: " + filter.getFilterStatus());
        System.out.println("text: " + filter.getFilterText());
        System.out.println("date: " + filter.getFilterData());

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> orderRoot = query.from(Order.class);
        List<Predicate> predicates = createPredicates(cb, orderRoot, filter);

        query.where(predicates.toArray(new Predicate[0]));

        // Sorting
        Expression<Object> orderByCase = cb.selectCase()
                .when(cb.equal(orderRoot.get("status"), OrderStatus.MAGAZYN), 1)
                .when(cb.equal(orderRoot.get("status"), OrderStatus.DOSTAWA), 2)
                .when(cb.equal(orderRoot.get("status"), OrderStatus.DOSTARCZONO), 3)
                .otherwise(4);

        query.orderBy(cb.asc(orderByCase));

        TypedQuery<Order> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Order> orderList;
        try {
            orderList = typedQuery.getResultList();
        } catch (Exception e) {
            System.out.println("Błąd podczas wykonywania zapytania: " + e.getMessage());
            e.printStackTrace();
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        Long count = 0L;
        System.out.println("Wykonanie zapytania countQuery...");
        try {
            // Użycie nowego `CriteriaQuery` do liczenia wyników z nowymi predykatami
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<Order> countRoot = countQuery.from(Order.class);
            List<Predicate> countPredicates = createPredicates(cb, countRoot, filter);
            countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
            count = entityManager.createQuery(countQuery).getSingleResult();
            System.out.println("Wynik zapytania countQuery: " + count);
        } catch (Exception e) {
            System.out.println("Błąd podczas wykonania zapytania countQuery: " + e.getMessage());
            e.printStackTrace();
        }

        return new PageImpl<>(orderList, pageable, count);
    }

    private List<Predicate> createPredicates(CriteriaBuilder cb, Root<Order> root, FilterOrderDTO filter) {
        List<Predicate> predicates = new ArrayList<>();

        // Filter by trackingNumber
        if (filter.getFilterText() != null && !filter.getFilterText().matches(".*[a-zA-Z]+.*")) {
            predicates.add(cb.like(root.get("trackingNumber"), "%" + filter.getFilterText() + "%"));
        }

        // Filter by date
        if (filter.getFilterData() != null) {
            predicates.add(cb.equal(root.get("creationDate"), filter.getFilterData()));
        }

        // Filter by status
        if (filter.getFilterStatus() != null && !"WSZYSTKIE".equals(filter.getFilterStatus())) {
            OrderStatus status = OrderStatus.valueOf(filter.getFilterStatus());
            predicates.add(cb.equal(root.get("status"), status));
        }

        // Filter by kind
        if (filter.getFilterKindEur() != null && ShipmentDimensions.EUR.equals(filter.getFilterKindEur()) &&
                (filter.getFilterKindHp() == null || !ShipmentDimensions.HP.equals(filter.getFilterKindHp()))) {
            predicates.add(cb.equal(root.get("dimensions"), ShipmentDimensions.EUR));
        } else if (filter.getFilterKindHp() != null && ShipmentDimensions.HP.equals(filter.getFilterKindHp()) &&
                (filter.getFilterKindEur() == null || !ShipmentDimensions.EUR.equals(filter.getFilterKindEur()))) {
            predicates.add(cb.equal(root.get("dimensions"), ShipmentDimensions.HP));
        }

        return predicates;
    }


    @Override
    public Long countOrdersByStatusForCurrentMonth(OrderStatus status) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Order> orderRoot = query.from(Order.class);
        List<Predicate> predicates = new ArrayList<>();

        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        predicates.add(cb.equal(cb.function("MONTH", Integer.class, orderRoot.get("creationDate")), currentMonth));
        predicates.add(cb.equal(cb.function("YEAR", Integer.class, orderRoot.get("creationDate")), currentYear));

        if (status != null) {
            predicates.add(cb.equal(orderRoot.get("status"), status));
        }

        query.select(cb.count(orderRoot)).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getSingleResult();
    }
}
