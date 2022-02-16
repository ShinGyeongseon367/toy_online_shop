package org.toy.jpashop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.toy.jpashop.domain.Order;
import org.toy.jpashop.domain.OrderSearch;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order findOne(long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        String psql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                psql += " where";
                isFirstCondition = false;
            } else {
                psql += " and";
            }

            psql += " o.status = :status";
        }

        if (orderSearch.getMemberName() != null) {
            if (isFirstCondition) {
                psql += " where";
                isFirstCondition = false;
            } else {
                psql += " and";
            }
            psql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(psql, Order.class).setMaxResults(1000);

        if(orderSearch.getOrderStatus() != null) {
            query.setParameter("status", orderSearch.getOrderStatus());
        }

        if(StringUtils.hasText(orderSearch.getMemberName())) {
            query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }
}
