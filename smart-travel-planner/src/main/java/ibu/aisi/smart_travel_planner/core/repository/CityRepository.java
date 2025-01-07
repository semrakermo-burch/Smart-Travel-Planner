package ibu.aisi.smart_travel_planner.core.repository;

import ibu.aisi.smart_travel_planner.core.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name); // Find a city by its name
}
