package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.utils.ServiceUtils;
import com.epam.esm.validator.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final PaginationValidator paginationValidator;

    @Override
    public UserDto getById(Long id) {
        User user = userDao.getById(id).orElseThrow(() -> new EntityNotFoundException("User not found", 40403));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAll(int page, int items) {
        long rowsCount = userDao.getRowsCount();
        paginationValidator.validatePaginationPage(page, items, rowsCount);
        List<User> users = userDao.getAll(ServiceUtils.calculateStartPos(page, items), items);
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userDao.getAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public long getRowCounts() {
        return userDao.getRowsCount();
    }

    @Override
    @Transactional
    public long save(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return userDao.save(user);
    }
}
