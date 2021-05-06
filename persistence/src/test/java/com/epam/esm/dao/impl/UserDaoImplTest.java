package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
@Transactional
public class UserDaoImplTest {
    private static final List<User> USER_LIST = Arrays.asList(new User(1L, "Ivan"), new User(2L, "Petr"));
    @Autowired
    UserDao userDao;

    @Test
    @Rollback
    public void testGetAllShouldReturnListUsers() {
        assertEquals(userDao.getAll(), USER_LIST);
    }
}

//    @Test
//    @Rollback
//    public void testGetAllShouldReturnLimitTags() {
//        assertEquals(tagDao.getAll(0,2), TAG_LIST);
//    }
//    @Test
//    @Rollback
//    public void testGetByIdShouldReturnTag() {
//        assertEquals(OPTIONAL_TAG, tagDao.getById(1L));
//    }
//    @Test
//    public void testGetRowsCountShouldReturnCountRowsInTable() {
//       assertEquals(2L,tagDao.getRowsCount());
//    }