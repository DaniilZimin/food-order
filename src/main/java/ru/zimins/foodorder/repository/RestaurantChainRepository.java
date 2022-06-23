package ru.zimins.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zimins.foodorder.model.RestaurantChain;

@Repository
public interface RestaurantChainRepository extends JpaRepository<RestaurantChain, Long> {
    boolean existsByNameIgnoreCase(String name);
}
