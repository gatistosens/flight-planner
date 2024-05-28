package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.domain.PageResult;
import io.codelex.flightplanner.domain.SearchFlightsRequest;

import java.util.List;


public interface FlightPlannerService {

    void saveFlight(Flight flight);

    void clearFlights();

    void deleteFlight(Long id);

    Flight findFlightById(Long id);

    List<Airport> getAirportDetails(String searchParam);

    PageResult<Flight> searchFlights(SearchFlightsRequest request);


}
