package ibu.aisi.smart_travel_planner.core.repository;

import ibu.aisi.smart_travel_planner.core.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long>, JpaSpecificationExecutor<Trip> {
    List<Trip> findByStatus(String status); // Find trips by status (Upcoming/Completed)
    List<Trip> findByCityName(String cityName); // Find trips by associated city's name
    List<Trip> findByUserEmailOrderByStartDateDesc(String email);
}