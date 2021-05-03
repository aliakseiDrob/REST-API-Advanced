package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.PaginationPageException;
import com.epam.esm.exception.TagValidationException;
import com.epam.esm.validator.impl.PaginationValidatorImpl;
import com.epam.esm.validator.impl.TagValidatorImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    private static List<Tag> tags;
    private static List<TagDto> tagsDto;

    @Mock
    private TagDaoImpl dao;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    PaginationValidatorImpl paginationValidator;
    @Mock
    TagValidatorImpl tagValidator;

    @InjectMocks
    private TagServiceImpl service;

    @BeforeAll
    public static void init() {
        tags = Arrays.asList(new Tag(1, "first"), new Tag(2, "second"));
        tagsDto = Arrays.asList(new TagDto(1, "first"), new TagDto(2, "second"));
    }

    @Test
    public void testGetByIdShouldReturnTagWhenTagExists() {
        when(dao.getById(1L)).thenReturn(Optional.of(tags.get(0)));
        when(modelMapper.map(any(), any())).thenReturn((tagsDto.get(0)));
        assertEquals(tagsDto.get(0), service.getById(1L));
        verify(dao, times(1)).getById(anyLong());
    }

    @Test
    public void testGetByIdShouldTrowExceptionWhenTagNotExists() {
        when(dao.getById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getById(anyLong()));
        verify(dao, times(1)).getById(anyLong());
    }

    @Test
    public void testGetAllShouldReturnAllTags() {
        when(dao.getAll()).thenReturn(tags);
        when(modelMapper.map(tags.get(0), TagDto.class)).thenReturn((tagsDto.get(0)));
        when(modelMapper.map(tags.get(1), TagDto.class)).thenReturn((tagsDto.get(1)));

        assertEquals(service.getAll(), tagsDto);
        verify(dao).getAll();
    }

    @Test
    void testGetAllShouldReturnPageTags() {
        when(dao.getAll(0, 2)).thenReturn(tags);
        when(modelMapper.map(tags.get(0), TagDto.class)).thenReturn((tagsDto.get(0)));
        when(modelMapper.map(tags.get(1), TagDto.class)).thenReturn((tagsDto.get(1)));
        assertEquals(service.getAll(1, 2), tagsDto);
        verify(dao).getAll(anyInt(), anyInt());
    }

    @Test
    void testGetAllShouldThrowExceptionWhenPageNotExist() {
        when(dao.getRowsCount()).thenReturn(1L);
        doThrow(PaginationPageException.class).when(paginationValidator).isPaginationPageExists(1, 1, 1L);
        assertThrows(PaginationPageException.class, () -> service.getAll(1, 1));
        verify(dao, times(1)).getRowsCount();
        verify(dao, times(0)).getAll(anyInt(), anyInt());
    }

    @Test
    void testGetRowCountsShouldReturnCountRowsInTable() {
        when(dao.getRowsCount()).thenReturn(1L);
        assertEquals(service.getRowCounts(), 1L);
        verify(dao, times(1)).getRowsCount();
    }

    @Test
    void testSaveShouldReturnTagId() {
        when(modelMapper.map(tagsDto.get(0), Tag.class)).thenReturn(tags.get(0));
        when(dao.save(tags.get(0))).thenReturn(1L);
        doNothing().when(tagValidator).isTagValid(any(TagDto.class));
        assertEquals(service.save(tagsDto.get(0)), 1L);
        verify(dao, times(1)).save(any(Tag.class));
    }

    @Test
    void testSaveShouldThrowExceptionWhenTagNameEmpty() {
        doThrow(TagValidationException.class).when(tagValidator).isTagValid(tagsDto.get(0));
        assertThrows(TagValidationException.class, () -> service.save(tagsDto.get(0)));
        verify(dao, times(0)).save(any(Tag.class));
    }

    @Test
    public void delete() {
        doNothing().when(dao).delete(anyLong());
        service.delete(anyLong());
        verify(dao, times(1)).delete(anyLong());
    }
}