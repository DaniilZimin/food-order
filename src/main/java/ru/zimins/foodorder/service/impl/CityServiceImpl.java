package ru.zimins.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.zimins.foodorder.model.City;
import ru.zimins.foodorder.repository.CityRepository;
import ru.zimins.foodorder.service.CityService;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository repository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.repository = cityRepository;
    }

    @Override
    public List<City> findAll() {
        return repository.findAll();
    }

    @Override
    public City findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Город с id = %d не найден".formatted(id)));
    }

    @Override
    public Page<City> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<City> findPage(String nameFilter, Pageable pageable) {
        if (nameFilter != null) {
            return repository.findAllByNameContainsIgnoreCase(nameFilter, pageable);
        }
        return repository.findAll(pageable);
    }
}
