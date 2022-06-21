package ru.zimins.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zimins.foodorder.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
