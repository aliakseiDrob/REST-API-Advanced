package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Interface for managing GiftCertificate entities
 */
public interface GiftCertificateDao {

    /**
     * Finds all GiftCertificates  from database
     *
     * @return list of GiftCertificate from database
     */
    List<GiftCertificate> getAll();

    /**
     * Returns list of GiftCertificates starting from startPos position in the table
     *
     * @param items    number of records from the table
     * @param startPos starting position for search in the table
     * @return List of GiftCertificates
     */
    List<GiftCertificate> getAll(int startPos, int items);

    /**
     * Finds GiftCertificate from database by id
     *
     * @param id GiftCertificate id
     * @return optional of GiftCertificate entity
     */
    Optional<GiftCertificate> getById(Long id);

    /**
     * Finds all  records GiftCertificate from database by Tag name
     *
     * @param names    names of Tags
     * @param nameSort is used to sort the result-set in ascending or descending order
     *                 by name, can be empty
     * @param dateSort is used to sort the result-set in ascending or descending order
     *                 create date, can be empty
     * @return list of GiftCertificates from database
     */
    List<GiftCertificate> findByTagNames(Set<String> names, String nameSort, String dateSort,
                                         int page,int items);

    /**
     * finds amount rows in database
     *
     * @return amount rows in database
     */
    long getRowsCount();

    /**
     * Finds all  records GiftCertificate from database by part name or description
     *
     * @param partNameOrDescription part name or description
     * @param nameSort              is used to sort the result-set in ascending or descending order
     *                              by name  can be empty
     * @param dateSort              is used to sort the result-set in ascending or descending order
     *                              by create date  can be empty
     * @return list of GiftCertificate from database
     */
    List<GiftCertificate> findByNameOrDescription(String partNameOrDescription, String nameSort,
                                                  String dateSort,int page,int items);

    /**
     * Finds all  records GiftCertificate from database by part name or description or Tag name
     *
     * @param names             names of Tags
     * @param nameOrDescription part name or description
     * @param nameSort          is used to sort the result-set in ascending or descending order
     *                          by name, can be empty
     * @param dateSort          is used to sort the result-set in ascending or descending order
     *                          by create date, can be empty
     * @return list of GiftCertificate from database
     */
    List<GiftCertificate> findByTagNameOrNameOrDescription(Set<String> names,
                                                           String nameOrDescription,
                                                           String nameSort, String dateSort,
                                                           int page, int items);

    /**
     * Saves  GiftCertificate in database
     *
     * @param giftCertificate GiftCertificate entity
     * @return GiftCertificate id
     */
    long save(GiftCertificate giftCertificate);

    /**
     * Updates  GiftCertificate in database
     *
     * @param certificate GiftCertificate entity for update
     * @return GiftCertificate id
     */

    GiftCertificate update(GiftCertificate certificate);
}
