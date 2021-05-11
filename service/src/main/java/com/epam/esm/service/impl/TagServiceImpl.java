package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.MostUsedTagDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.TagEntityException;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ServiceUtils;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final ModelMapper modelMapper;
    private final TagValidator tagValidator;
    private final PaginationValidator paginationValidator;


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
        paginationValidator.validatePaginationPage(page, items, rowsCount);
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
    @Transactional
    public long save(TagDto tagDto) {
        tagValidator.validateTag(tagDto);
        if (tagDto.getId() != 0) {
            tagDto.setId(0);
        }
        Tag tag = modelMapper.map(tagDto, Tag.class);
        try {
            return tagDao.save(tag);
        } catch (
                DataIntegrityViolationException exception) {
            throw new TagEntityException("Tag already exists", 40009);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag tag = tagDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found", 40402));
        tagDao.delete(tag);
    }

    @Override
    public MostUsedTagDto getMostWidelyUsedTag(Long userId) {
        MostWidelyUsedTag mostWidelyUsedTag = null;
        try {
            mostWidelyUsedTag = tagDao.getMostWildlyUsedTag(userId);
        } catch (EmptyResultDataAccessException ex) {
            throw new TagEntityException("Tag not found", 40406);
        }
        return modelMapper.map(mostWidelyUsedTag, MostUsedTagDto.class);
    }
}
