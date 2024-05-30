package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.domain.PageResult;
import io.codelex.flightplanner.domain.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class FlightPlannerDBService implements FlightPlannerService {

    private final FlightPlannerDBRepository flightPlannerDBRepository;
    private final AirportRepository airportRepository;

    public FlightPlannerDBService(FlightPlannerDBRepository flightPlannerDBRepository, AirportRepository airportRepository) {
        this.flightPlannerDBRepository = flightPlannerDBRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public void clearFlights() {
        flightPlannerDBRepository.deleteAll();
    }

    @Override
    public void saveFlight(Flight flight) {
        validateAndSaveAirports(flight);
        List<Flight> existingFlights = flightPlannerDBRepository.findAll();
        FlightValidator.checkIfDuplicateFlight(flight, existingFlights);
        FlightValidator.checkIfSameAirportToAndFrom(flight);
        FlightValidator.checkIfDatesAreValid(flight);
        flightPlannerDBRepository.save(flight);
    }

    @Override
    public void deleteFlight(Long id) {
        flightPlannerDBRepository.deleteById(id);
    }

    @Override
    public Flight findFlightById(Long id) {
        return flightPlannerDBRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }

    @Override
    public List<Airport> getAirportDetails(String searchParam) {
        return flightPlannerDBRepository.findAirport(searchParam);
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightsRequest request) {
        if (request.getFrom().equalsIgnoreCase(request.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "From and To are the same");
        }
        List<Flight> flights = flightPlannerDBRepository.findFlights(request.getFrom(), request.getTo(), request.getDepartureDate());
        return new PageResult<>(0L, (long) flights.size(), flights);
    }

    private void validateAndSaveAirports(Flight flight) {
        if (flight.getFrom() == null || flight.getTo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Both departure and arrival airports must be provided.");
        }
        Airport fromAirport = flight.getFrom();
        if (fromAirport.getId() == null) {
            fromAirport = airportRepository.save(fromAirport);
            flight.setFrom(fromAirport);
        }
        Airport toAirport = flight.getTo();
        if (toAirport.getId() == null) {
            toAirport = airportRepository.save(toAirport);
            flight.setTo(toAirport);
        }
    }
}
