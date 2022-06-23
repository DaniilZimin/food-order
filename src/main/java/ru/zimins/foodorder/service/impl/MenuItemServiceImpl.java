package ru.zimins.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.zimins.foodorder.model.MenuItem;
import ru.zimins.foodorder.model.MenuItemCategory;
import ru.zimins.foodorder.model.Restaurant;
import ru.zimins.foodorder.repository.MenuItemCategoryRepository;
import ru.zimins.foodorder.repository.MenuItemRepository;
import ru.zimins.foodorder.repository.RestaurantRepository;
import ru.zimins.foodorder.service.MenuItemService;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository repository;

    private final RestaurantRepository restaurantRepository;

    private final MenuItemCategoryRepository menuItemCategoryRepository;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository repository, RestaurantRepository restaurantRepository, MenuItemCategoryRepository menuItemCategoryRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemCategoryRepository = menuItemCategoryRepository;
    }

    @Override
    public MenuItem create(MenuItem model) {

        Long menuItemCategoryId = model.getMenuItemCategory().getId();
        assert menuItemCategoryId != null;

        MenuItemCategory menuItemCategory = menuItemCategoryRepository.findById(menuItemCategoryId)
                .orElseThrow(() -> new NotFoundException("Категория пункта меню с id = %d не найдена".formatted(menuItemCategoryId)));

        Long restaurantId = model.getRestaurant().getId();
        assert restaurantId != null;

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Ресторан с id = %d не найден".formatted(restaurantId)));

        MenuItem menuItem = repository.save(model);

        menuItemCategory.addMenuItem(menuItem);
        menuItemCategoryRepository.save(menuItemCategory);

        restaurant.addMenuItem(menuItem);
        restaurantRepository.save(restaurant);

        return menuItem;
    }

    @Override
    public List<MenuItem> findAllByRestaurantId(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ресторан с id = %d не найден".formatted(id)));
        return restaurant.getMenuItems();
    }

    @Override
    public MenuItem findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пункт меню с id = %d не найден".formatted(id)));
    }

    @Override
    public Page<MenuItem> findPage(Long id, String nameFilter, Pageable pageable) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ресторан с id = %d не найден".formatted(id)));

        return nameFilter == null
                ? repository.findAllByRestaurant(restaurant, pageable)
                : repository.findAllByRestaurantAndNameContainsIgnoreCase(restaurant, nameFilter, pageable);
    }

    @Override
    public MenuItem update(MenuItem model) {

        Long id = model.getId();
        assert id != null;

        MenuItem menuItem = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пункт меню с id = %d не найден".formatted(id)));

        if (model.getName() != null) {
            menuItem.setName(model.getName());
        }

        if (model.getDescription() != null) {
            menuItem.setDescription(model.getDescription());
        }

        if (model.getPrice() != null) {
            menuItem.setPrice(model.getPrice());
        }

        if (model.getWeight() != null) {
            menuItem.setWeight(model.getWeight());
        }

        if (model.getUnit() != null) {
            menuItem.setUnit(model.getUnit());
        }

        if (model.getComposition() != null) {
            menuItem.setComposition(model.getComposition());
        }

        if (model.getKcal() != null) {
            menuItem.setKcal(model.getKcal());
        }

        if (model.getProteins() != null) {
            menuItem.setProteins(model.getProteins());
        }

        if (model.getFats() != null) {
            menuItem.setFats(model.getFats());
        }

        if (model.getCarbohydrates() != null) {
            menuItem.setCarbohydrates(model.getCarbohydrates());
        }

        if (model.getMenuItemCategory() != null) {
            Long menuItemCategoryId = model.getMenuItemCategory().getId();
            assert menuItemCategoryId != null;

            MenuItemCategory menuItemCategory = menuItemCategoryRepository.findById(menuItemCategoryId)
                    .orElseThrow(() -> new NotFoundException("Категория пункта меню с id = %d не найдена".formatted(menuItemCategoryId)));

            menuItem.setMenuItemCategory(menuItemCategory);
        }

        if (model.getRestaurant() != null) {
            Long restaurantId = model.getRestaurant().getId();
            assert restaurantId != null;

            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new NotFoundException("Ресторан с id = %d не найден".formatted(restaurantId)));

            menuItem.setRestaurant(restaurant);
        }

        return repository.save(menuItem);
    }

    @Override
    public MenuItem deleteById(Long id) {
        MenuItem menuItem = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пункт меню с id = %d не найден".formatted(id)));

        repository.deleteById(id);

        return menuItem;
    }
}
