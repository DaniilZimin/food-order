package ru.zimins.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.zimins.foodorder.exception.ValidationException;
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
        if (model.getMenuItemCategory() == null || model.getRestaurant() == null) {
            return repository.save(model);
        }

        MenuItemCategory menuItemCategory = getMenuItemCategory(model);

        Restaurant restaurant = getRestaurant(model);

        validate(model, restaurant, menuItemCategory);

        MenuItem menuItem = repository.save(model);

        menuItemCategory.addMenuItem(menuItem);
        menuItemCategoryRepository.save(menuItemCategory);

        restaurant.addMenuItem(menuItem);
        restaurantRepository.save(restaurant);

        return menuItem;
    }

    @Override
    public List<MenuItem> findAllByRestaurantId(Long id) {
        return getRestaurant(id).getMenuItems();
    }

    @Override
    public MenuItem findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("?????????? ???????? ?? id = %d ???? ????????????".formatted(id)));
    }

    @Override
    public Page<MenuItem> findPage(Long id, String nameFilter, Pageable pageable) {
        return nameFilter == null
                ? repository.findAllByRestaurant(getRestaurant(id), pageable)
                : repository.findAllByRestaurantAndNameContainsIgnoreCase(getRestaurant(id), nameFilter, pageable);
    }

    @Override
    public MenuItem update(MenuItem model) {
        findById(model.getId());

        if (model.getRestaurant() == null) {
            throw new NotFoundException("?????????????? ????????????????");
        }

        if (model.getMenuItemCategory() == null) {
            throw new NotFoundException("?????????????? ?????????????????? ???????????? ????????");
        }

        validate(model, getRestaurant(model), getMenuItemCategory(model));

        return repository.save(model);
    }

    @Override
    public MenuItem deleteById(Long id) {
        MenuItem menuItem = findById(id);

        repository.deleteById(id);

        return menuItem;
    }

    private Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("???????????????? ?? id = %d ???? ????????????".formatted(id)));
    }

    private Restaurant getRestaurant(MenuItem model) {
        Long id = model.getRestaurant().getId();
        assert id != null;

        return getRestaurant(id);
    }

    private MenuItemCategory getMenuItemCategory(MenuItem model) {
        Long id = model.getMenuItemCategory().getId();
        assert id != null;

        return menuItemCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("?????????????????? ???????????? ???????? ?? id = %d ???? ??????????????".formatted(id)));
    }

    private void validate(MenuItem model, Restaurant restaurant, MenuItemCategory menuItemCategory) {
        String menuItemName = model.getName();
        String restaurantName = restaurant.getName();

        if (menuItemCategory.getRestaurant() != restaurant && menuItemCategory.getRestaurant() != null) {
            throw new ValidationException("?????????????????? ???????????? ???????? '%s' ?????????????????????? ?? ?????????????????? %s ?? ???? ???????????????? ??????????"
                    .formatted(menuItemCategory.getName(), restaurantName));
        }

        if (repository.existsByNameIgnoreCaseAndRestaurant(menuItemName, restaurant)) {
            throw new ValidationException("?????????? ???????? '%s' ?????? ???????? ?? ?????????????????? %s".formatted(menuItemName, restaurantName));
        }
    }}
