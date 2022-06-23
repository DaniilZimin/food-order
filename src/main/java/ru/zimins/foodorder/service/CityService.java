package ru.zimins.foodorder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.zimins.foodorder.model.City;
import ru.zimins.foodorder.service.common.CrudService;

public interface CityService extends CrudService<City, Long> {

    /**
     * Поиск страницы сущностой по имени
     *
     * @param nameFilter имя сущности
     * @param pageable набор параметров для поиска страницы сущностей
     * @return станица сущностей
     */
    Page<City> findPage(String nameFilter, Pageable pageable);
}
