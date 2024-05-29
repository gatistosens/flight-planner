package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.domain.PageResult;
import io.codelex.flightplanner.domain.SearchFlightsRequest;
import io.codelex.flightplanner.util.FlightValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class FlightPlannerDBService implements FlightPlannerService {

    private final FlightPlannerRepository flightPlannerRepository;

    public FlightPlannerDBService(FlightPlannerRepository flightPlannerRepository) {
        this.flightPlannerRepository = flightPlannerRepository;
    }

    public void clearFlights() {
        flightPlannerRepository.clearFlights();
    }

    public void saveFlight(Flight flight) {
        List<Flight> existingFlights = flightPlannerRepository.getFlights();
        FlightValidator.checkIfDuplicateFlight(flight, existingFlights);
        FlightValidator.checkIfSameAirportToAndFrom(flight);
        FlightValidator.checkIfDatesAreValid(flight);
        flightPlannerRepository.saveFlight(flight);
    }

    public void deleteFlight(Long id) {
        flightPlannerRepository.deleteFlightById(id);
    }

    public Flight findFlightById(Long id) {
        Flight flight = flightPlannerRepository.getFlightById(id);
        if (flight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
        }
        return flight;
    }

    public List<Airport> getAirportDetails(String searchParam) {
        return flightPlannerRepository.getAirport(searchParam);
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest request) {
        if (request.getFrom().equalsIgnoreCase(request.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "From and To are the same");
        }
        List<Flight> flights = flightPlannerRepository.searchFlights(request);
        return new PageResult<>(0L, (long) flights.size(), flights);
    }
}
