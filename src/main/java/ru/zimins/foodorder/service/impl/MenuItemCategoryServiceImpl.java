package ru.zimins.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;
import ru.zimins.foodorder.model.MenuItemCategory;
import ru.zimins.foodorder.model.Restaurant;
import ru.zimins.foodorder.repository.MenuItemCategoryRepository;
import ru.zimins.foodorder.repository.RestaurantRepository;
import ru.zimins.foodorder.service.MenuItemCategoryService;

import java.util.List;

@Service
public class MenuItemCategoryServiceImpl implements MenuItemCategoryService {

    private final MenuItemCategoryRepository repository;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuItemCategoryServiceImpl(MenuItemCategoryRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public MenuItemCategory create(MenuItemCategory model) {
        if (model.getRestaurant() != null) {
            MenuItemCategory menuItemCategory = repository.save(model);

            Restaurant restaurant = getRestaurant(model);

            restaurant.addMenuItemCategory(menuItemCategory);
            restaurantRepository.save(restaurant);
        }

        return repository.save(model);
    }

    @Override
    public List<MenuItemCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<MenuItemCategory> findPage(String nameFilter, Pageable pageable) {
        return repository.findAllByNameContainsIgnoreCase(nameFilter, pageable);
    }

    @Override
    public MenuItemCategory update(MenuItemCategory model) {
        MenuItemCategory menuItemCategory = getMenuItemCategory(model.getId());

        if (model.getName() != null) {
            menuItemCategory.setName(model.getName());
        }

        if (model.getRestaurant() != null) {
            menuItemCategory.setRestaurant(getRestaurant(model));
        }

        return repository.save(menuItemCategory);
    }

    @Override
    public MenuItemCategory deleteById(Long id) {
        MenuItemCategory menuItemCategory = getMenuItemCategory(id);

        if (menuItemCategory.getRestaurant() == null) {
            throw new RuntimeException("Категорию пункта меню с нулевым рестораном нельзя удалять");
        }

        if (!CollectionUtils.isEmpty(menuItemCategory.getMenuItems())) {
            throw new RuntimeException("В категории '%s' есть пункты меню, поэтому удалить её не удалось".formatted(menuItemCategory.getName()));
        }

        repository.deleteById(id);

        return menuItemCategory;
    }

    private Restaurant getRestaurant(MenuItemCategory model) {
        Long id = model.getRestaurant().getId();
        assert id != null;

        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ресторан с id = %d не найден".formatted(id)));
    }

    private MenuItemCategory getMenuItemCategory(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория пункта меню с id = %d не найдена".formatted(id)));
    }
}
