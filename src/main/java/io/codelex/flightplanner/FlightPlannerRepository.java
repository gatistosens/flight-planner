package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class FlightPlannerRepository {

    private final List<Flight> flights = new ArrayList<>();
    private final AtomicLong lastId = new AtomicLong(0);


    public void saveFlight(Flight flight) {
        flight.setId(lastId.incrementAndGet());
        flights.add(flight);
    }


    public Flight getFlightById(long id) {
        Flight flightDetails = null;
        for (Flight flight : flights) {
            if (flight.getId() == id) {
                flightDetails = flight;
            }
        }
        return flightDetails;
    }

    public void deleteFlightById(long id) {
        flights.removeIf(flight -> flight.getId() == id);
    }


    public List<Airport> getAirport(String searchParam) {
        Pattern pattern = Pattern.compile(Pattern.quote(searchParam.trim()), Pattern.CASE_INSENSITIVE);

        return flights.stream()
                .map(Flight::getFrom)
                .distinct()
                .filter(airport -> pattern.matcher(airport.getAirport()).find() ||
                        pattern.matcher(airport.getCountry()).find() ||
                        pattern.matcher(airport.getCity()).find())
                .toList();
    }


    public List<Flight> getFlights() {
        return flights;
    }

    public void clearFlights() {
        flights.clear();
    }

    @Override
    public String toString() {
        return "FlightPlannerRepository{" +
                "flights=" + flights +
                ", lastId=" + lastId +
                '}';
    }
}