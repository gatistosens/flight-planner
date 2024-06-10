package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface FlightPlannerDBRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT  f.from FROM Flight f WHERE " +
            "LOWER(TRIM(f.from.airport)) LIKE LOWER(CONCAT('%', TRIM(:searchParam), '%')) OR " +
            "LOWER(TRIM(f.from.city)) LIKE LOWER(CONCAT('%', TRIM(:searchParam), '%')) OR " +
            "LOWER(TRIM(f.from.country)) LIKE LOWER(CONCAT('%', TRIM(:searchParam), '%'))")
    List<Airport> findAirport(String searchParam);


    @Query("SELECT f FROM Flight f WHERE LOWER(f.from.airport) = LOWER(:from) " +
            "AND LOWER(f.to.airport) = LOWER(:to) " +
            "AND CAST(f.departureTime AS DATE) = :departureDate ")
    List<Flight> findFlights(String from, String to, LocalDate departureDate);
}
