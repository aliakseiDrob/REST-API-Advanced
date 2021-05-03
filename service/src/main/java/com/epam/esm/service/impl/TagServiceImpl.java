package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ServiceUtils;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.TagValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final ModelMapper modelMapper;
    private final TagValidator tagValidator;
    private final PaginationValidator paginationValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, ModelMapper modelMapper, TagValidator tagValidator, PaginationValidator paginationValidator) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
        this.tagValidator = tagValidator;
        this.paginationValidator = paginationValidator;
    }

    @Override
    public List<TagDto> getAll() {
        List<Tag> tags = tagDao.getAll();
        return tags.stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TagDto> getAll(int page, int items) {
        long rowsCount = tagDao.getRowsCount();
        paginationValidator.isPaginationPageExists(page, items, rowsCount);
        List<Tag> tags = tagDao.getAll(ServiceUtils.calculateStartPos(page, items), items);
        return tags.stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto getById(long id) {
        Tag tag = tagDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found", 40402));
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public long getRowCounts() {
        return tagDao.getRowsCount();
    }

    @Override
    public long save(TagDto tagDto) {
        tagValidator.isTagValid(tagDto);
        Tag tag = modelMapper.map(tagDto, Tag.class);
        return tagDao.save(tag);
    }

    @Override
    public void delete(Long id) {
        tagDao.delete(id);
    }
}
