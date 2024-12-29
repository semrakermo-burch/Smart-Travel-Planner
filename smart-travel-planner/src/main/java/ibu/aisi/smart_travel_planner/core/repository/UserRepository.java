package ibu.aisi.smart_travel_planner.core.repository;

import ibu.aisi.smart_travel_planner.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Find a user by their email
}
