package ru.zimins.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;
import ru.zimins.foodorder.exception.ValidationException;
import ru.zimins.foodorder.model.RestaurantChain;
import ru.zimins.foodorder.repository.RestaurantChainRepository;
import ru.zimins.foodorder.service.RestaurantChainService;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantChainServiceImpl implements RestaurantChainService {

    private final RestaurantChainRepository repository;

    @Autowired
    public RestaurantChainServiceImpl(RestaurantChainRepository repository) {
        this.repository = repository;
    }

    @Override
    public RestaurantChain create(RestaurantChain model) {
        if (repository.existsByNameIgnoreCase(model.getName())) {
            throw new ValidationException("Сеть ресторанов с name = '%s' уже есть в базе данных".formatted(model.getName()));
        }
        return repository.save(model);
    }

    @Override
    public List<RestaurantChain> findAll() {
        return repository.findAll();
    }

    @Override
    public RestaurantChain findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Сеть ресторанов с id = %d не найдена".formatted(id)));
    }

    @Override
    public Page<RestaurantChain> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public RestaurantChain update(RestaurantChain model) {
        Long id = model.getId();
        assert id != null;

        RestaurantChain restaurantChain = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Сеть ресторанов с id = %d не найдена".formatted(id)));

        restaurantChain.setName(model.getName());

        return repository.save(restaurantChain);
    }

    @Override
    public RestaurantChain deleteById(Long id) {
        RestaurantChain restaurantChain = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Сеть ресторанов с id = %d не найдена".formatted(id)));

        if (!CollectionUtils.isEmpty(restaurantChain.getRestaurants())) {
            throw new ValidationException("В сети ресторанов '%s' есть рестораны, поэтому удалить её не удалось".formatted(restaurantChain.getName()));
        }

        repository.deleteById(id);

        return restaurantChain;
    }
}
