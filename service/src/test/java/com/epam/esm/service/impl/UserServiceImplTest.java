package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.PaginationPageException;
import com.epam.esm.validator.impl.PaginationValidatorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final int PAGE_NUMBER = 1;
    private static final int ITEMS_ON_PAGE = 1;
    private static List<User> users;
    private static List<UserDto> usersDto;

    @Mock
    UserDaoImpl dao;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    PaginationValidatorImpl paginationValidator;
    @InjectMocks
    UserServiceImpl service;

    @BeforeAll
    public static void init() {
        users = Arrays.asList(new User(1, "first", null), new User(2, "second", null));
        usersDto = Arrays.asList(new UserDto(1, "first"), new UserDto(2, "second"));
    }

    @Test
    public void testGetByIdShouldReturnUserWhenUserExists() {
        //when
        when(dao.getById(1L)).thenReturn(Optional.of(users.get(0)));
        when(modelMapper.map(any(), any())).thenReturn((usersDto.get(0)));
        //then
        assertEquals(usersDto.get(0), service.getById(1L));
        verify(dao, times(1)).getById(anyLong());
        verify(modelMapper, times(1)).map(any(), any());
        verify(paginationValidator, times(0)).validatePaginationPage(anyInt(), anyInt(), anyLong());
    }

    @Test
    public void testGetByIdShouldTrowExceptionWhenUserNotExists() {
        //when
        when(dao.getById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> service.getById(anyLong()));
        verify(dao, times(1)).getById(anyLong());
        verify(modelMapper, times(0)).map(any(), any());
        verify(paginationValidator, times(0)).validatePaginationPage(anyInt(), anyInt(), anyLong());
    }

    @Test
    public void testGetAllShouldReturnAllUsers() {
        //when
        when(dao.getAll()).thenReturn(users);
        when(modelMapper.map(users.get(0), UserDto.class)).thenReturn((usersDto.get(0)));
        when(modelMapper.map(users.get(1), UserDto.class)).thenReturn((usersDto.get(1)));
        //then
        assertEquals(usersDto, service.getAll());
        verify(dao, times(1)).getAll();
        verify(modelMapper, times(users.size())).map(any(), any());
        verify(paginationValidator, times(0)).validatePaginationPage(anyInt(), anyInt(), anyLong());
    }

    @Test
    void testGetAllShouldReturnPageUsers() {
        //when
        when(dao.getAll(0, ITEMS_ON_PAGE)).thenReturn(users);
        when(modelMapper.map(users.get(0), UserDto.class)).thenReturn((usersDto.get(0)));
        when(modelMapper.map(users.get(1), UserDto.class)).thenReturn((usersDto.get(1)));
        //then
        assertEquals(usersDto, service.getAll(PAGE_NUMBER, ITEMS_ON_PAGE));
        verify(dao, times(1)).getAll(anyInt(), anyInt());
        verify(modelMapper, times(users.size())).map(any(), any());
        verify(paginationValidator, times(1)).validatePaginationPage(anyInt(), anyInt(), anyLong());
    }

    @Test
    void testGetAllShouldThrowExceptionWhenPageNotExist() {
        //when
        when(dao.getRowsCount()).thenReturn(1L);
        doThrow(PaginationPageException.class).when(paginationValidator).validatePaginationPage(PAGE_NUMBER, ITEMS_ON_PAGE, 1L);
        //then
        assertThrows(PaginationPageException.class, () -> service.getAll(PAGE_NUMBER, ITEMS_ON_PAGE));
        verify(dao, times(1)).getRowsCount();
        verify(dao, times(0)).getAll(anyInt(), anyInt());
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    void testGetRowCountsShouldReturnCountRowsInTable() {
        //when
        when(dao.getRowsCount()).thenReturn(1L);
        //then
        assertEquals(1L, service.getRowCounts());
        verify(dao, times(1)).getRowsCount();
    }

    @Test
    void testSaveShouldReturnUserId() {
        //when
        when(modelMapper.map(usersDto.get(0), User.class)).thenReturn(users.get(0));
        when(dao.save(users.get(0))).thenReturn(users.get(0).getId());
        //then
        assertEquals(1L, service.save(usersDto.get(0)));
        verify(dao, times(1)).save(any(User.class));
        verify(modelMapper, times(1)).map(any(), any());
    }
}
