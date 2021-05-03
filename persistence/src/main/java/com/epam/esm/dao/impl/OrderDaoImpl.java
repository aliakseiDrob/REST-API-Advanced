package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> getAllUserOrders(Long userId) {
        return entityManager.createQuery("select a from Order a where a.user.id=: userId", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Optional<Order> getUserOrder(long userId, long orderId) {
        return Optional.ofNullable((Order) entityManager
                .createQuery("select a FROM Order a WHERE a.id = :orderId AND a.user.id = :userId", Order.class)
                .setParameter("userId", userId)
                .setParameter("orderId", orderId)
                .getSingleResult());
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public long getRowsCount() {
        return (long) entityManager.createQuery("SELECT COUNT(a) FROM Order a").getSingleResult();
    }

    @Override
    public MostWidelyUsedTag getMostWildlyUsedTag(Long userId) {

        return (MostWidelyUsedTag) entityManager.createNativeQuery(
                "SELECT tag.id AS tag_id, tag.name AS tag_name , MAX(o.order_cost) AS highest_cost " +
                        "FROM tag " +
                        "JOIN gift_certificate_tag gct ON gct.tag_id = tag.id " +
                        "JOIN orders o ON o.certificate_id = gct.gift_certificate_id " +
                        "WHERE o.user_id = :userId " +
                        "GROUP BY tag.id " +
                        "ORDER BY COUNT(tag.id) DESC " +
                        "LIMIT 1",
                "mostWidelyUsedTagMapper")
                .setParameter("userId", userId)
                .getSingleResult();
    }

}
