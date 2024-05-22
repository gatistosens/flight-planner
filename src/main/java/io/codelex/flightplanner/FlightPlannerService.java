package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.domain.PageResult;
import io.codelex.flightplanner.domain.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
public class FlightPlannerService {

    private final FlightPlannerRepository flightPlannerRepository;

    public FlightPlannerService(FlightPlannerRepository flightPlannerRepository) {
        this.flightPlannerRepository = flightPlannerRepository;
    }

    public void clearFlights() {
        flightPlannerRepository.clearFlights();
    }

    public void saveFlight(Flight flight) {
        List<Flight> existingFlights = flightPlannerRepository.getFlights();
        checkIfDuplicateFlight(flight, existingFlights);
        checkIfSameAirportToAndFrom(flight);
        checkIfDatesAreValid(flight);
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


    private static void checkIfDuplicateFlight(Flight flight, List<Flight> existingFlights) {
        if (existingFlights.contains(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists.");
        }
    }


    private static void checkIfSameAirportToAndFrom(Flight flight) {
        if (flight.getFrom() == null || flight.getTo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Both departure and arrival airports must be provided.");
        }
        if (flight.getFrom().getAirport().trim().equalsIgnoreCase(flight.getTo().getAirport().trim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departure and arrival airports cannot be the same.");
        }
    }

    private static void checkIfDatesAreValid(Flight flight) {
        if (flight.getDepartureTime().compareTo(flight.getArrivalTime()) >= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departure time must be before arrival time.");
        }
    }


}
