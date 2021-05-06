package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
@Transactional
public class UserDaoImplTest {

    private static final int START_POSITION_FOR_SEARCH = 0;
    private static final int QUANTITY_ROWS = 2;
    private static final long EXPECTED_QUANTITY_ROWS = 2L;
    private static final List<User> USER_LIST = Arrays.asList(new User(1L, "Ivan"), new User(2L, "Petr"));
    private static final List<User> USER_LIST_AFTER_SAVE = Arrays.asList(new User(1L, "Ivan"),
            new User(2L, "Petr"), new User("Stepan"));
    private static final User USER_INSTANCE = new User(1L, "Ivan");
    private static final String USER_FOR_SAVE = "Stepan";
    @Autowired
    UserDao userDao;

    @Test
    @Rollback
    public void testGetAllShouldReturnAllUsers() {
        //then
        assertEquals(userDao.getAll(), USER_LIST);
    }


    @Test
    @Rollback
    public void testGetAllShouldReturnRangeUsers() {
        //then
        assertEquals(userDao.getAll(START_POSITION_FOR_SEARCH, QUANTITY_ROWS), USER_LIST);
    }

    @Test
    @Rollback
    public void testGetByIdShouldReturnUser() {
        //given
        Optional<User> expectedUser = Optional.of(USER_INSTANCE);
        //then
        assertEquals(expectedUser, userDao.getById(1L));
    }

    @Test
    public void testGetRowsCountShouldReturnCountRowsInTable() {
        //then
        assertEquals(EXPECTED_QUANTITY_ROWS, userDao.getRowsCount());
    }

    @Test
    public void testSaveShouldSaveUserInDb() {
        //then
        userDao.save(new User(USER_FOR_SAVE));
        assertEquals(USER_LIST_AFTER_SAVE, userDao.getAll());
    }
}