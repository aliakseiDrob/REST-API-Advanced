package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class TagDaoImpl implements TagDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Tag> getAll() {
        return entityManager.createQuery("SELECT a FROM Tag a", Tag.class).getResultList();
    }

    @Override
    public List<Tag> getAll(int startPos, int items) {
        return entityManager.createQuery("SELECT a FROM Tag a", Tag.class)
                .setFirstResult(startPos)
                .setMaxResults(items)
                .getResultList();
    }

    @Override
    public Optional<Tag> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public long getRowsCount() {
        return (long) entityManager.createQuery("SELECT COUNT(a) FROM Tag a").getSingleResult();
    }

    @Override
    @Transactional
    public long save(Tag tag) {
       entityManager.persist(tag);
       return tag.getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
       Tag tag = entityManager.find(Tag.class,id);
       entityManager.remove(tag);
    }

    @Override
    public Set<Tag> saveTags(Set<Tag> tags) {
        saveNewTags(tags);
        assignTagsIds(tags);
        return tags;
    }

    private void saveNewTags(Set<Tag> tags) {
        tags.stream()
                .filter(tag -> !getAll().contains(tag))
                .forEach(tag -> tag.setId(save(tag)));
    }

    private void assignTagsIds(Set<Tag> tags) {
        List<Tag> allTags = getAll();
        tags.stream()
                .filter(allTags::contains)
                .forEach(tag -> tag.setId(allTags.get(allTags.indexOf(tag)).getId()));
    }
}
