package com.epam.esm.dao.impl;

import com.epam.esm.dao.SortType;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @PersistenceContext
    EntityManager entityManager;

    private static final String GET_CERTIFICATES_BY_TAG_NAME = "SELECT c FROM GiftCertificate c JOIN c.tags tag " +
            "WHERE c.isAvailable = 1 AND tag.name ${conditions}  GROUP BY c.id " +
            "HAVING COUNT(c) >= :count  ${value}";
    private static final String GET_CERTIFICATE_BY_PART_NAME_OR_DESCRIPTION = "FROM GiftCertificate c " +
            "WHERE c.isAvailable = 1 AND (c.name LIKE :text OR c.description LIKE :text) %s";
    private static final String GET_CERTIFICATE_BY_TAG_NAME_OR_BY_PART_NAME_OR_DESCRIPTION = "SELECT DISTINCT c " +
            "FROM GiftCertificate c JOIN c.tags tag " +
            "WHERE c.isAvailable = 1 AND (tag.name ${conditions} " +
            "OR c.name LIKE :text OR c.description LIKE :text) ${value}";

    @Override
    public List<GiftCertificate> getAll() {
        return entityManager.createQuery("select a from GiftCertificate a WHERE a.isAvailable =1", GiftCertificate.class).getResultList();
    }

    @Override
    public List<GiftCertificate> getAll(int startPos, int items) {
        return entityManager.createQuery("SELECT a FROM GiftCertificate a WHERE a.isAvailable =1", GiftCertificate.class)
                .setFirstResult(startPos)
                .setMaxResults(items)
                .getResultList();
    }

    @Override
    public Optional<GiftCertificate> getById(Long id) {
        return Optional.ofNullable(entityManager
                .createQuery("SELECT a FROM GiftCertificate a WHERE a.id =: id AND a.isAvailable =1", GiftCertificate.class)
                .setParameter("id",id).getSingleResult());
    }

    @Override
    public List<GiftCertificate> findByTagNames(Set<String> names, String nameSort, String dateSort) {
        TypedQuery<GiftCertificate> query = entityManager
                .createQuery(insertParametersInSql(defineConditions(names),
                        defineSortType(extractFieldsForSearch(nameSort, dateSort)), GET_CERTIFICATES_BY_TAG_NAME), GiftCertificate.class);
        query.setParameter("count", (long) names.size());
        setQueryParameters(query, names);
        return query.getResultList();
    }

    @Override
    public long getRowsCount() {
        return (long) entityManager.createQuery("SELECT COUNT(a) FROM GiftCertificate a WHERE a.isAvailable=1").getSingleResult();
    }

    private String defineConditions(Set<String> tagNames) {
        return produceConditions(prepareConditions(tagNames));
    }

    private Set<String> prepareConditions(Set<String> conditions) {
        return conditions.stream()
                .filter(condition -> condition.trim().length() > 0)
                .map(condition -> condition = ("=:").concat(condition.replaceAll(" ", "")))
                .collect(Collectors.toSet());
    }

    private String produceConditions(Set<String> conditions) {
        return String.join(" OR ".concat("tag.").concat("name"), conditions);
    }

    private String produceParams(List<String> params) {
        return "ORDER BY " + String.join(", ", params);
    }

    private List<String> extractFieldsForSearch(String nameSort, String dateSort) {
        List<String> fieldsList = new ArrayList<>();
        fieldsList.add(SortType.isSortTypeValid(nameSort) ? "c.name " + nameSort : null);
        fieldsList.add(SortType.isSortTypeValid(dateSort) ? "c.createDate " + dateSort : null);
        return fieldsList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private String insertParametersInSql(String conditions, String sortTypes, String query) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("conditions", conditions);
        parameters.put("value", sortTypes);
        return new StringSubstitutor(parameters).replace(query);
    }

    private void setQueryParameters(Query query, Set<String> parameters) {
        parameters.stream()
                .filter(parameter -> parameter.trim().length() > 0)
                .forEach(parameter -> {
                    String condition = parameter.replaceAll(" ", "");
                    query.setParameter(condition, parameter);
                });
    }

    private String defineSortType(List<String> sortTypes) {
        return isSortTypeExists(sortTypes) ? getTableFieldsForSorting(sortTypes) : "";
    }

    private boolean isSortTypeExists(List<String> sortTypes) {
        return CollectionUtils.isNotEmpty(sortTypes);
    }

    private String getTableFieldsForSorting(List<String> sortTypes) {
        return isSortTypeExists(sortTypes) ? produceParams(sortTypes) : "";
    }

    @Override
    public List<GiftCertificate> findByNameOrDescription(String partNameOrDescription, String nameSort, String dateSort) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(insertSqlQuerySubString(
                defineSortType(extractFieldsForSearch(nameSort, dateSort)), GET_CERTIFICATE_BY_PART_NAME_OR_DESCRIPTION), GiftCertificate.class);
        query.setParameter("text", prepareParameterForInsertingToSqlScript(partNameOrDescription));
        return query.getResultList();
    }

    private String prepareParameterForInsertingToSqlScript(String partNameOrDescription) {
        return "%" + partNameOrDescription + "%";
    }

    private String insertSqlQuerySubString(String subString, String sqlQuery) {
        return String.format(sqlQuery, subString);
    }

    @Override
    public List<GiftCertificate> findByTagNameOrNameOrDescription(Set<String> names, String nameOrDescription, String nameSort, String dateSort) {
        TypedQuery<GiftCertificate> query = entityManager
                .createQuery(insertParametersInSql(defineConditions(names),
                        defineSortType(extractFieldsForSearch(nameSort, dateSort)),
                        GET_CERTIFICATE_BY_TAG_NAME_OR_BY_PART_NAME_OR_DESCRIPTION), GiftCertificate.class);
        query.setParameter("text", prepareParameterForInsertingToSqlScript(nameOrDescription));
        setQueryParameters(query, names);
        return query.getResultList();
    }

    @Override
    public long save(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate.getId();
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate certificate) {
        entityManager.merge(certificate);
        return entityManager.find(GiftCertificate.class, certificate.getId());
    }

    @Override
    @Transactional
    public void delete(GiftCertificate certificate) {
        entityManager.merge(certificate);
    }
}
