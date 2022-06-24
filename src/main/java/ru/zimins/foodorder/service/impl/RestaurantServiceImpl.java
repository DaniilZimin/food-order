package ru.zimins.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
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
            throw new RuntimeException("Ресторан с name = '%s' уже есть в базе данных".formatted(model.getName()));
        }

        if (repository.existsByAddressIgnoreCase(model.getAddress())) {
            throw new RuntimeException("Ресторан с адресом = '%s' уже есть в базе данных".formatted(model.getAddress()));
        }

        Restaurant restaurant = repository.save(model);

        if (model.getRestaurantChain() != null) {
            getRestaurantChain(model).addRestaurant(restaurant);
            restaurantChainRepository.save(getRestaurantChain(model));
        }

        if (model.getCity() != null) {
            getCity(model).addRestaurant(restaurant);
            cityRepository.save(getCity(model));
        }

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
        Restaurant restaurant = findById(model.getId());

        if (model.getName() != null) {
            restaurant.setName(model.getName());
        }

        if (model.getAddress() != null) {
            restaurant.setAddress(model.getAddress());
        }

        if (model.getRestaurantChain() != null) {
            restaurant.setRestaurantChain(getRestaurantChain(model));
        }

        if (model.getCity() != null) {
            restaurant.setCity(getCity(model));
        }

        return repository.save(restaurant);
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
