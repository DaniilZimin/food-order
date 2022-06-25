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
        if (model.getMenuItemCategory() != null && model.getRestaurant() != null) {

            MenuItem menuItem = repository.save(model);

            MenuItemCategory menuItemCategory = getMenuItemCategory(model);

            menuItemCategory.addMenuItem(menuItem);
            menuItemCategoryRepository.save(menuItemCategory);

            Restaurant restaurant = getRestaurant(model);

            restaurant.addMenuItem(menuItem);
            restaurantRepository.save(restaurant);

            return menuItem;
        }

        return repository.save(model);
    }

    @Override
    public List<MenuItem> findAllByRestaurantId(Long id) {
        return getRestaurant(id).getMenuItems();
    }

    @Override
    public MenuItem findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пункт меню с id = %d не найден".formatted(id)));
    }

    @Override
    public Page<MenuItem> findPage(Long id, String nameFilter, Pageable pageable) {
        return nameFilter == null
                ? repository.findAllByRestaurant(getRestaurant(id), pageable)
                : repository.findAllByRestaurantAndNameContainsIgnoreCase(getRestaurant(id), nameFilter, pageable);
    }

    @Override
    public MenuItem update(MenuItem model) {
        MenuItem menuItem = findById(model.getId());

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
            menuItem.setMenuItemCategory(getMenuItemCategory(model));
        }

        if (model.getRestaurant() != null) {
            menuItem.setRestaurant(getRestaurant(model));
        }

        return repository.save(menuItem);
    }

    @Override
    public MenuItem deleteById(Long id) {
        MenuItem menuItem = findById(id);

        repository.deleteById(id);

        return menuItem;
    }

    private Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ресторан с id = %d не найден".formatted(id)));
    }

    private Restaurant getRestaurant(MenuItem model) {
        Long id = model.getRestaurant().getId();
        assert id != null;

        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ресторан с id = %d не найден".formatted(id)));
    }

    private MenuItemCategory getMenuItemCategory(MenuItem model) {
        Long id = model.getMenuItemCategory().getId();
        assert id != null;

        return menuItemCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория пункта меню с id = %d не найдена".formatted(id)));
    }
}
