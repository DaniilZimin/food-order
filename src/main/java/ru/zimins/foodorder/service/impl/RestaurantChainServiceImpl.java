package ru.zimins.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
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
        Optional<RestaurantChain> byName = repository.findByName(model.getName());
        if (byName.isPresent()) {
            throw new RuntimeException("Сеть ресторанов с name = '%s' уже есть в базе данных".formatted(model.getName()));
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
        Optional<RestaurantChain> optional = repository.findById(id);
        if (optional.isPresent()) {
            optional.get().setName(model.getName());
            return repository.save(optional.get());
        }
        throw new NotFoundException("Сеть ресторанов с id = %d не найдена".formatted(id));
    }

    @Override
    public RestaurantChain deleteById(Long id) {
        Optional<RestaurantChain> optional = repository.findById(id);
        if (optional.isPresent()) {
            if (optional.get().getRestaurants().isEmpty()) {
                repository.deleteById(id);
                return optional.get();
            } else {
                throw new RuntimeException("В сети ресторанов '%s' есть рестораны, поэтому удалить её не удалось".formatted(optional.get().getName()));
            }
        }
        throw new NotFoundException("Сеть ресторанов с id = %d не найдена".formatted(id));
    }
}
