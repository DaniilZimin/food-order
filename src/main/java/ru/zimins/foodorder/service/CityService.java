package ru.zimins.foodorder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.zimins.foodorder.model.City;
import ru.zimins.foodorder.service.common.CrudService;

public interface CityService extends CrudService<City, Long> {

    Page<City> findPage(String nameFilter, Pageable pageable);
}
