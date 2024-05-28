package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.domain.PageResult;
import io.codelex.flightplanner.domain.SearchFlightsRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class FlightPlannerController {

    private final FlightPlannerService flightPlannerService;

    public FlightPlannerController(FlightPlannerService flightPlannerService) {
        this.flightPlannerService = flightPlannerService;
    }

    @GetMapping("/admin-api/flights/{id}")
    public Flight adminGetFlight(@PathVariable Long id) {
        return flightPlannerService.findFlightById(id);
    }

    @PostMapping("/testing-api/clear")
    public void clear() {
        flightPlannerService.clearFlights();
    }

    @PutMapping("/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight adminSaveFlight(@Valid @RequestBody Flight flight) {
        flightPlannerService.saveFlight(flight);
        return flight;
    }

    @DeleteMapping("/admin-api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void adminDeleteFlight(@PathVariable Long id) {
        flightPlannerService.deleteFlight(id);
    }

    @GetMapping("/api/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@RequestParam String search) {
        return flightPlannerService.getAirportDetails(search);
    }

    @PostMapping("/api/flights/search")
    @ResponseStatus(HttpStatus.OK)
    public PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightsRequest request) {
        return flightPlannerService.searchFlights(request);
    }

    @GetMapping("/api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightById(@PathVariable Long id) {
        return flightPlannerService.findFlightById(id);
    }
}
