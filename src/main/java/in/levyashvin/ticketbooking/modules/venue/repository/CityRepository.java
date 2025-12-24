package in.levyashvin.ticketbooking.modules.venue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.levyashvin.ticketbooking.modules.venue.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
