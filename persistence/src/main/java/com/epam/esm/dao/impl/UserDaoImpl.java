package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAll(int startPos, int items) {
        return entityManager.createQuery("SELECT a from User a ORDER BY a.id", User.class)
                .setFirstResult(startPos)
                .setMaxResults(items)
                .getResultList();
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("SELECT a from User a", User.class).getResultList();
    }

    @Override
    public long getRowsCount() {
        return (long) entityManager.createQuery("SELECT COUNT(a) FROM User a").getSingleResult();
    }

    @Override
    @Transactional
    public long save(User user) {
        entityManager.persist(user);
        return user.getId();
    }
}
