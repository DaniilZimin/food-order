package ru.zimins.foodorder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.zimins.foodorder.model.MenuItem;
import ru.zimins.foodorder.service.common.CrudService;

import java.util.List;

public interface MenuItemService extends CrudService<MenuItem, Long> {

    /**
     * Получение полного списка пунктов меню по идентификатору ресторана
     *
     * @param id идентификатор ресторана
     * @return список пунктов меню
     */
    List<MenuItem> findAllByRestaurantId(Long id);

    /**
     * Поиск страницы пунктов меню по идентификатору ресторана и имени
     *
     * @param id идентификатор ресторана
     * @param nameFilter название пункта меню
     * @param pageable набор параметров для поиска страницы пунктов меню
     * @return страница пунктов меню
     */
    Page<MenuItem> findPage(Long id, String nameFilter, Pageable pageable);
}
