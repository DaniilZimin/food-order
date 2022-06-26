package ru.zimins.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;
import ru.zimins.foodorder.exception.ValidationException;
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
        Restaurant restaurant = null;

        if (model.getRestaurant() != null) {
            restaurant = getRestaurant(model);
        }

        validate(model, restaurant);

        MenuItemCategory menuItemCategory = repository.save(model);

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
        getMenuItemCategory(model.getId());

        validate(model, getRestaurant(model));

        return repository.save(model);
    }

    @Override
    public MenuItemCategory deleteById(Long id) {
        MenuItemCategory menuItemCategory = getMenuItemCategory(id);

        if (menuItemCategory.getRestaurant() == null) {
            throw new ValidationException("Данную категорию удалить нельзя!");
        }

        if (!CollectionUtils.isEmpty(menuItemCategory.getMenuItems())) {
            throw new ValidationException("В категории '%s' есть пункты меню, поэтому удалить её нельзя".formatted(menuItemCategory.getName()));
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

    private void validate(MenuItemCategory model, Restaurant restaurant) {
        String name = model.getName();

        MenuItemCategory byNameIgnoreCase = repository.findByNameIgnoreCase(name);

        if (byNameIgnoreCase != null) {
            if (byNameIgnoreCase.getRestaurant() == null) {
                throw new ValidationException("Категория пунктов меню '%s' является общей".formatted(name));
            }
            if (restaurant == byNameIgnoreCase.getRestaurant()) {
                assert restaurant != null;
                throw new ValidationException("Категория пунктов меню с названием '%s' уже есть в ресторане %s".formatted(name, restaurant.getName()));
            }
        }
    }
}
