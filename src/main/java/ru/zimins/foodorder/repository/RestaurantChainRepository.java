package ru.zimins.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zimins.foodorder.model.RestaurantChain;

import java.util.Optional;

@Repository
public interface RestaurantChainRepository extends JpaRepository<RestaurantChain, Long> {
    Optional<RestaurantChain> findByName(String Name);
}
