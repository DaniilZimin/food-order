package ru.zimins.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;
import ru.zimins.foodorder.exception.ValidationException;
import ru.zimins.foodorder.model.MenuItem;
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
        Restaurant restaurant = getRestaurant(model);

        List<MenuItemCategory> categories = repository.findByNameIgnoreCase(model.getName());

        validate(model, restaurant, categories);

        MenuItemCategory menuItemCategory = saveCategory(model, restaurant, categories);

        if (restaurant != null) {
            restaurant.addMenuItemCategory(menuItemCategory);
            restaurantRepository.save(restaurant);
        }

        return menuItemCategory;
    }

    @Override
    public List<MenuItemCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<MenuItemCategory> findPage(String nameFilter, Pageable pageable) {
        return nameFilter == null
                ? repository.findAll(pageable)
                : repository.findAllByNameContainsIgnoreCase(nameFilter, pageable);
    }

    @Override
    public MenuItemCategory update(MenuItemCategory model) {
        MenuItemCategory menuItemCategory = getMenuItemCategory(model.getId());

        if (menuItemCategory.getRestaurant() == null) {
            throw new ValidationException("Общую категорию изменять нельзя!");
        }

        Restaurant restaurant = getRestaurant(model);

        List<MenuItemCategory> categories = repository.findByNameIgnoreCase(model.getName());

        validate(model, restaurant, categories);

        return saveCategory(model, restaurant, categories);
    }

    @Override
    public MenuItemCategory deleteById(Long id) {
        MenuItemCategory menuItemCategory = getMenuItemCategory(id);

        if (menuItemCategory.getRestaurant() == null) {
            throw new ValidationException("Данную категорию удалить нельзя!");
        }

        if (!CollectionUtils.isEmpty(menuItemCategory.getMenuItems())) {
            throw new ValidationException("В категории '%s' есть пункты меню, поэтому удалить её нельзя"
                    .formatted(menuItemCategory.getName()));
        }

        repository.deleteById(id);

        return menuItemCategory;
    }

    private Restaurant getRestaurant(MenuItemCategory model) {
        if (model.getRestaurant() == null) {
            return null;
        }

        Long id = model.getRestaurant().getId();
        assert id != null;

        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ресторан с id = %d не найден".formatted(id)));
    }

    private MenuItemCategory getMenuItemCategory(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория пункта меню с id = %d не найдена".formatted(id)));
    }

    private void validate(MenuItemCategory model, Restaurant restaurant, List<MenuItemCategory> categories) {
        String categoryName = model.getName();

        if (CollectionUtils.isEmpty(categories)) {
            return;
        }

        for (MenuItemCategory menuItemCategory : categories) {
            if (menuItemCategory.getRestaurant() == null) {
                throw new ValidationException("Категория пунктов меню '%s' является общей".formatted(categoryName));
            }

            if (menuItemCategory.getRestaurant() == restaurant) {
                assert restaurant != null;
                throw new ValidationException("Категория пунктов меню с названием '%s' уже есть в ресторане %s"
                        .formatted(categoryName, restaurant.getName()));
            }
        }
    }

    private MenuItemCategory saveCategory(MenuItemCategory model, Restaurant restaurant, List<MenuItemCategory> categories) {
        MenuItemCategory savedCategory = repository.save(model);

        if (restaurant != null || CollectionUtils.isEmpty(categories)) {
            return savedCategory;
        }

        for (MenuItemCategory menuItemCategory : categories) {
            List<MenuItem> menuItems = menuItemCategory.getMenuItems();

            if (menuItems != null) {
                for (MenuItem menuItem : menuItems) {
                    menuItem.setMenuItemCategory(savedCategory);
                }
            }

            if (menuItemCategory.getRestaurant() != null) {
                repository.delete(menuItemCategory);
            }
        }

        return savedCategory;
    }
}
