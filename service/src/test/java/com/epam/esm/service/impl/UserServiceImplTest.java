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
        when(dao.getById(1L)).thenReturn(Optional.of(users.get(0)));
        when(modelMapper.map(any(), any())).thenReturn((usersDto.get(0)));
        assertEquals(usersDto.get(0), service.getById(1L));
        verify(dao, times(1)).getById(anyLong());
    }

    @Test
    public void testGetByIdShouldTrowExceptionWhenUserNotExists() {
        when(dao.getById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getById(anyLong()));
        verify(dao, times(1)).getById(anyLong());
    }

    @Test
    public void testGetAllShouldReturnAllUsers() {
        when(dao.getAll()).thenReturn(users);
        when(modelMapper.map(users.get(0), UserDto.class)).thenReturn((usersDto.get(0)));
        when(modelMapper.map(users.get(1), UserDto.class)).thenReturn((usersDto.get(1)));

        assertEquals(service.getAll(), usersDto);
        verify(dao).getAll();
    }

    @Test
    void testGetAllShouldReturnPageUsers() {
        when(dao.getAll(0, 2)).thenReturn(users);
        when(modelMapper.map(users.get(0), UserDto.class)).thenReturn((usersDto.get(0)));
        when(modelMapper.map(users.get(1), UserDto.class)).thenReturn((usersDto.get(1)));
        assertEquals(service.getAll(1, 2), usersDto);
        verify(dao).getAll(anyInt(), anyInt());
    }

    @Test
    void testGetAllShouldThrowExceptionWhenPageNotExist() {
        when(dao.getRowsCount()).thenReturn(1L);
        doThrow(PaginationPageException.class).when(paginationValidator).isPaginationPageExists(1, 1, 1L);
        assertThrows(PaginationPageException.class, () -> service.getAll(1, 1));
        verify(dao,times(1)).getRowsCount();
        verify(dao,times(0)).getAll(anyInt(),anyInt());
    }

    @Test
    void testGetRowCountsShouldReturnCountRowsInTable() {
        when(dao.getRowsCount()).thenReturn(1L);
        assertEquals(service.getRowCounts(), 1L);
        verify(dao,times(1)).getRowsCount();
    }

    @Test
    void testSaveShouldReturnUserId() {
        when(modelMapper.map(usersDto.get(0), User.class)).thenReturn(users.get(0));
        when(dao.save(users.get(0))).thenReturn(1L);
        assertEquals(service.save(usersDto.get(0)), 1L);
        verify(dao,times(1)).save(any(User.class));
          }
}
