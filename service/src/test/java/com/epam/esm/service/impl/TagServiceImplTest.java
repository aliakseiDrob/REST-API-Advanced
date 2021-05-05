package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.MostUsedTagDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.MostWidelyUsedTag;
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

import java.math.BigDecimal;
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

    private static final int PAGE_NUMBER = 1;
    private static final int ITEMS_ON_PAGE = 1;
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
    public void testGetAllShouldReturnAllTags() {
        //when
        when(dao.getAll()).thenReturn(tags);
        when(modelMapper.map(tags.get(0), TagDto.class)).thenReturn((tagsDto.get(0)));
        when(modelMapper.map(tags.get(1), TagDto.class)).thenReturn((tagsDto.get(1)));
        //then
        assertEquals(tagsDto, service.getAll());
        verify(dao, times(1)).getAll();
        verify(modelMapper, times(tags.size())).map(any(), any());
        verify(paginationValidator, times(0)).validatePaginationPage(anyInt(), anyInt(), anyLong());
    }

    @Test
    void testGetAllShouldReturnPaginatePageTags() {
        //when
        when(dao.getRowsCount()).thenReturn(2L);
        doNothing().when(paginationValidator).validatePaginationPage(anyInt(), anyInt(), anyLong());
        when(dao.getAll(0, ITEMS_ON_PAGE)).thenReturn(tags);
        when(modelMapper.map(tags.get(0), TagDto.class)).thenReturn((tagsDto.get(0)));
        when(modelMapper.map(tags.get(1), TagDto.class)).thenReturn((tagsDto.get(1)));
        //then
        assertEquals(tagsDto, service.getAll(PAGE_NUMBER, ITEMS_ON_PAGE));
        verify(dao).getAll(anyInt(), anyInt());
        verify(modelMapper, times(tags.size())).map(any(), any());
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
    }

    @Test
    public void testGetByIdShouldReturnTagWhenTagExists() {
        //when
        when(dao.getById(1L)).thenReturn(Optional.of(tags.get(0)));
        when(modelMapper.map(any(), any())).thenReturn((tagsDto.get(0)));
        //then
        assertEquals(tagsDto.get(0), service.getById(1L));
        verify(dao, times(1)).getById(anyLong());
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    public void testGetByIdShouldTrowExceptionWhenTagNotExists() {
        //when
        when(dao.getById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> service.getById(anyLong()));
        verify(dao, times(1)).getById(anyLong());
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    void testGetRowCountsShouldReturnCountRowsInTable() {
        //when
        when(dao.getRowsCount()).thenReturn(1L);
        //then
        assertEquals(service.getRowCounts(), 1L);
        verify(dao, times(1)).getRowsCount();
    }

    @Test
    void testSaveShouldReturnTagId() {
        //when
        doNothing().when(tagValidator).validateTag(any(TagDto.class));
        when(modelMapper.map(tagsDto.get(0), Tag.class)).thenReturn(tags.get(0));
        when(dao.save(tags.get(0))).thenReturn(1L);
        //then
        assertEquals(1L, service.save(tagsDto.get(0)));
        verify(dao, times(1)).save(any(Tag.class));
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    void testSaveShouldThrowExceptionWhenTagNameEmpty() {
        //when
        doThrow(TagValidationException.class).when(tagValidator).validateTag(tagsDto.get(0));
        //then
        assertThrows(TagValidationException.class, () -> service.save(tagsDto.get(0)));
        verify(tagValidator, times(1)).validateTag(any(TagDto.class));
        verify(dao, times(0)).save(any(Tag.class));
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void delete() {
        //when
        doNothing().when(dao).delete(anyLong());
        service.delete(anyLong());
        //then
        verify(dao, times(1)).delete(anyLong());
    }

    @Test
    public void testGetMostWidelyUsedTagShouldReturnMostUsedTag() {
        // given
        MostWidelyUsedTag mostWidelyUsedTag = new MostWidelyUsedTag(1L, "first", new BigDecimal("100"));
        MostUsedTagDto mostUsedTagDto = new MostUsedTagDto(new TagDto(1L, "first"), new BigDecimal("100"));
        //when
        when(dao.getMostWildlyUsedTag(anyLong())).thenReturn(mostWidelyUsedTag);
        when(modelMapper.map(any(), any())).thenReturn(mostUsedTagDto);
        //then
        assertEquals(mostUsedTagDto, service.getMostWidelyUsedTag(1L));
    }
}