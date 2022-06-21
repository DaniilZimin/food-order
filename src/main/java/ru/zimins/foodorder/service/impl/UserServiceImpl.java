package ru.zimins.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.zimins.foodorder.model.User;
import ru.zimins.foodorder.repository.UserRepository;
import ru.zimins.foodorder.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = %d не найден".formatted(id)));
    }

    @Override
    public Page<User> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
