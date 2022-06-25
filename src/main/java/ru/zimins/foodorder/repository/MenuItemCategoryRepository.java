package ru.zimins.foodorder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zimins.foodorder.model.MenuItemCategory;

@Repository
public interface MenuItemCategoryRepository extends JpaRepository<MenuItemCategory, Long> {
    Page<MenuItemCategory> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
}
