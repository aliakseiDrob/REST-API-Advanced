package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

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
        return entityManager
                .createQuery("select a FROM Order a WHERE a.id = :orderId AND a.user.id = :userId", Order.class)
                .setParameter("userId", userId)
                .setParameter("orderId", orderId)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Order createOrder(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public long getRowsCount() {
        return (long) entityManager.createQuery("SELECT COUNT(a) FROM Order a").getSingleResult();
    }
}
