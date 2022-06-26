package ru.zimins.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.zimins.foodorder.exception.ValidationException;
import ru.zimins.foodorder.model.City;
import ru.zimins.foodorder.model.Restaurant;
import ru.zimins.foodorder.model.RestaurantChain;
import ru.zimins.foodorder.repository.CityRepository;
import ru.zimins.foodorder.repository.RestaurantChainRepository;
import ru.zimins.foodorder.repository.RestaurantRepository;
import ru.zimins.foodorder.service.RestaurantService;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository repository;

    private final RestaurantChainRepository restaurantChainRepository;

    private final CityRepository cityRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository, RestaurantChainRepository restaurantChainRepository, CityRepository cityRepository) {
        this.repository = repository;
        this.restaurantChainRepository = restaurantChainRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public Restaurant create(Restaurant model) {
        if (repository.existsByNameIgnoreCase(model.getName())) {
            throw new ValidationException("Ресторан с name = '%s' уже есть в базе данных".formatted(model.getName()));
        }

        if (repository.existsByAddressIgnoreCase(model.getAddress())) {
            throw new ValidationException("Ресторан с адресом = '%s' уже есть в базе данных".formatted(model.getAddress()));
        }

        RestaurantChain restaurantChain = null;

        if (model.getRestaurantChain() != null) {
            restaurantChain = getRestaurantChain(model);
        }

        City city;

        if (model.getCity() != null) {
            city = getCity(model);
        } else {
            throw new NotFoundException("Укажите Город");
        }

        Restaurant restaurant = repository.save(model);

        if (restaurantChain != null) {
            restaurantChain.addRestaurant(restaurant);
            restaurantChainRepository.save(restaurantChain);
        }

        city.addRestaurant(restaurant);
        cityRepository.save(city);

        return restaurant;
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    @Override
    public Restaurant findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ресторан с id = %d не найден".formatted(id)));
    }

    @Override
    public Page<Restaurant> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Restaurant update(Restaurant model) {
        findById(model.getId());

        if (model.getCity() == null) {
            throw new NotFoundException("Укажите Город");
        }

        getCity(model);

        if (model.getRestaurantChain() != null) {
            getRestaurantChain(model);
        }

        return repository.save(model);
    }

    @Override
    public Restaurant deleteById(Long id) {
        Restaurant restaurant = findById(id);

        repository.deleteById(id);

        return restaurant;
    }

    private City getCity(Restaurant model) {
        Long id = model.getCity().getId();
        assert id != null;

        return cityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Город с id = %d не найден".formatted(id)));
    }

    private RestaurantChain getRestaurantChain(Restaurant model) {
        Long id = model.getRestaurantChain().getId();
        assert id != null;

        return restaurantChainRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Сеть ресторанов с id = %d не найдена".formatted(id)));
    }
}
