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
import java.util.Optional;

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
            Long restaurantChainId = model.getRestaurantChain().getId();
            assert restaurantChainId != null;

            Optional<RestaurantChain> restaurantChain = restaurantChainRepository.findById(restaurantChainId);

            restaurantChain.ifPresent(chain -> {
                chain.addRestaurant(restaurant);
                restaurantChainRepository.save(chain);
            });
        }

        Long cityId = model.getCity().getId();
        assert cityId != null;

        Optional<City> city = cityRepository.findById(cityId);

        city.ifPresent(value -> {
            value.addRestaurant(restaurant);
            cityRepository.save(value);
        });

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
        Long id = model.getId();
        assert id != null;

        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ресторан с id = %d не найден".formatted(id)));

        if (model.getName() != null) {
            restaurant.setName(model.getName());
        }

        if (model.getAddress() != null) {
            restaurant.setAddress(model.getAddress());
        }

        if (model.getRestaurantChain() != null) {
            Long restaurantChainId = model.getRestaurantChain().getId();
            assert restaurantChainId != null;

            RestaurantChain restaurantChain = restaurantChainRepository.findById(restaurantChainId)
                    .orElseThrow(() -> new NotFoundException("Сеть ресторанов с id = %d не найдена".formatted(restaurantChainId)));

            restaurant.setRestaurantChain(restaurantChain);
        }

        if (model.getCity() != null) {
            Long cityId = model.getCity().getId();
            assert cityId != null;

            City city = cityRepository.findById(cityId)
                    .orElseThrow(() -> new NotFoundException("Город с id = %d не найден".formatted(cityId)));

            restaurant.setCity(city);
        }

        return repository.save(restaurant);
    }

    @Override
    public Restaurant deleteById(Long id) {
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new NotFoundException("Ресторан с id = %d не найден".formatted(id)));

        repository.deleteById(id);

        return restaurant;
    }
}
