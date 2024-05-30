package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class FlightValidator {

    public static void checkIfDuplicateFlight(Flight flight, List<Flight> existingFlights) {
        if (existingFlights.contains(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists.");
        }
    }

    public static void checkIfSameAirportToAndFrom(Flight flight) {
        if (flight.getFrom() == null || flight.getTo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Both departure and arrival airports must be provided.");
        }
        if (flight.getFrom().getAirport().trim().equalsIgnoreCase(flight.getTo().getAirport().trim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departure and arrival airports cannot be the same.");
        }
    }

    public static void checkIfDatesAreValid(Flight flight) {
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime()) || flight.getDepartureTime().isEqual(flight.getArrivalTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departure time must be before arrival time.");
        }
    }
}
