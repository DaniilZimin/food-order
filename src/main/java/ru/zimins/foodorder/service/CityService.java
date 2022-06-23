package ru.zimins.foodorder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.zimins.foodorder.model.City;
import ru.zimins.foodorder.service.common.CrudService;

public interface CityService extends CrudService<City, Long> {

    /**
     * Поиск страницы городов по имени
     *
     * @param nameFilter имя города
     * @param pageable набор параметров для поиска страницы городов
     * @return станица городово
     */
    Page<City> findPage(String nameFilter, Pageable pageable);
}
