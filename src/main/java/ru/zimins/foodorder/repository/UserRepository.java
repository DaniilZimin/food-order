package ru.zimins.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zimins.foodorder.model.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        FROM User u
        WHERE lower(u.username) = :username
           OR lower(u.email) = :username
           OR lower(u.phone) = :username
    """)
    Optional<User> findByUsernameOrEmailOrPhone(@Param("username") String username);

}
