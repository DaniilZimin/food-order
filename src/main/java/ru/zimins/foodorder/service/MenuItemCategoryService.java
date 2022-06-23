package ru.zimins.foodorder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.zimins.foodorder.model.MenuItemCategory;
import ru.zimins.foodorder.service.common.CrudService;

public interface MenuItemCategoryService extends CrudService<MenuItemCategory, Long> {
    /**
     * Поиск страницы категорий пунктов меню по имени
     *
     * @param nameFilter название категории пунктов меню
     * @param pageable набор параметров для поиска страницы категорий пунктов меню
     * @return страница категорий пунктов меню
     */
    Page<MenuItemCategory> findPage(String nameFilter, Pageable pageable);
}
