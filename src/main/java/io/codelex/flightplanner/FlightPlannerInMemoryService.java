package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.domain.PageResult;
import io.codelex.flightplanner.domain.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class FlightPlannerInMemoryService implements FlightPlannerService {

    private final FlightPlannerInMemoryRepository flightPlannerInMemoryRepository;

    public FlightPlannerInMemoryService(FlightPlannerInMemoryRepository flightPlannerRepository) {
        this.flightPlannerInMemoryRepository = flightPlannerRepository;
    }

    @Override
    public void clearFlights() {
        flightPlannerInMemoryRepository.clearFlights();
    }

    @Override
    public void saveFlight(Flight flight) {
        List<Flight> existingFlights = flightPlannerInMemoryRepository.getFlights();
        FlightValidator.checkIfDuplicateFlight(flight, existingFlights);
        FlightValidator.checkIfSameAirportToAndFrom(flight);
        FlightValidator.checkIfDatesAreValid(flight);
        flightPlannerInMemoryRepository.saveFlight(flight);
    }

    @Override
    public void deleteFlight(Long id) {
        flightPlannerInMemoryRepository.deleteFlightById(id);
    }

    @Override
    public Flight findFlightById(Long id) {
        Flight flight = flightPlannerInMemoryRepository.getFlightById(id);
        if (flight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
        }
        return flight;
    }

    @Override
    public List<Airport> getAirportDetails(String searchParam) {
        return flightPlannerInMemoryRepository.getAirport(searchParam);
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightsRequest request) {
        if (request.getFrom().equalsIgnoreCase(request.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "From and To are the same");
        }
        List<Flight> flights = flightPlannerInMemoryRepository.searchFlights(request);
        return new PageResult<>(0L, (long) flights.size(), flights);
    }
}
