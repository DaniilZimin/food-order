package ru.zimins.foodorder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zimins.foodorder.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Page<City> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
}
