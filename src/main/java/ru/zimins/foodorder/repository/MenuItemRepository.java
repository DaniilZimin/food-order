package ru.zimins.foodorder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zimins.foodorder.model.MenuItem;
import ru.zimins.foodorder.model.MenuItemCategory;
import ru.zimins.foodorder.model.Restaurant;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Page<MenuItem> findAllByRestaurantAndNameContainsIgnoreCase(Restaurant restaurant, String name, Pageable pageable);
    Page<MenuItem> findAllByRestaurant(Restaurant restaurant, Pageable pageable);
    boolean existsByNameIgnoreCaseAndRestaurant(String name, Restaurant restaurant);
    boolean existsByNameIgnoreCaseAndMenuItemCategory(String name, MenuItemCategory menuItemCategory);
}
