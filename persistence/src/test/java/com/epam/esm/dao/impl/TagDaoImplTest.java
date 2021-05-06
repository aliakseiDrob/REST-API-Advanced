package com.epam.esm.dao.impl;


import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
@Transactional
public class TagDaoImplTest {

    private static final List<Tag> TAG_LIST = Arrays.asList(new Tag(1L, "first"), new Tag(2L, "second"));
    private static final List<Tag> TAG_LIST_AFTER_SAVE_TAG = Arrays.asList(new Tag(1L, "first"),
            new Tag(2L, "second"), new Tag(3L, "third"));
    private static final List<Tag> TAG_LIST_AFTER_DELETE_TAG = Collections.singletonList(new Tag(2L, "second"));
    private static final Optional<Tag> OPTIONAL_TAG = Optional.of(new Tag(1L, "first"));
    private static final Tag TAG_INSTANCE_FOR_SAVE = new Tag("third");
    private static final int START_POSITION_FOR_SEARCH = 0;
    private static final int QUANTITY_ROWS = 2;
    private static final long EXPECTED_QUANTITY_ROWS = 2L;
    private static Set<Tag> setTagsForSave;
    private static List<Tag> listTagsAfterSaveInDb;


    @Autowired
    TagDao tagDao;

    private static void setTagInstanceForSave() {
        setTagsForSave = new HashSet<>();
        setTagsForSave.add(new Tag(5L, "third"));
        setTagsForSave.add(new Tag(7L, "fourth"));
    }

    private static void setTagInstanceAfterSave() {
        listTagsAfterSaveInDb = Arrays.asList(new Tag(1L, "first"), new Tag(2L, "second"),
                new Tag(3L, "third"), new Tag(4L, "fourth"));
    }

    @Test
    @Rollback
    public void testGetAllShouldReturnListTags() {
        assertEquals(tagDao.getAll(), TAG_LIST);
    }

    @Test
    @Rollback
    public void testGetAllShouldReturnLimitTags() {
        assertEquals(tagDao.getAll(START_POSITION_FOR_SEARCH, QUANTITY_ROWS), TAG_LIST);
    }

    @Test
    @Rollback
    public void testGetByIdShouldReturnTag() {
        assertEquals(OPTIONAL_TAG, tagDao.getById(1L));
    }

    @Test
    public void testGetRowsCountShouldReturnCountRowsInTable() {
        assertEquals(EXPECTED_QUANTITY_ROWS, tagDao.getRowsCount());
    }


    @Test
    @Rollback
    void testSaveShouldSaveTag() {
        tagDao.save(TAG_INSTANCE_FOR_SAVE);
        assertEquals(TAG_LIST_AFTER_SAVE_TAG, tagDao.getAll());
    }

    @Test
    @Rollback
    void TestDeleteShouldDeleteTag() {
        tagDao.delete(1L);
        assertEquals(TAG_LIST_AFTER_DELETE_TAG, tagDao.getAll());
    }

    @Test
    @Rollback
    void testSaveTagsShouldReturnSavedTags() {
        setTagInstanceForSave();
        setTagInstanceAfterSave();
        assertEquals(tagDao.saveTags(setTagsForSave), setTagsForSave);
        assertEquals(tagDao.getAll(), listTagsAfterSaveInDb);
    }
}
