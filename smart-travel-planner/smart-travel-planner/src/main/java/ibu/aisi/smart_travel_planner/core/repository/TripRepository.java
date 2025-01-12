package ibu.aisi.smart_travel_planner.core.repository;

import ibu.aisi.smart_travel_planner.core.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUserEmailOrderByStartDateDesc(String email);
}