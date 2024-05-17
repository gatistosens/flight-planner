package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
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
    public Flight getFlight(@PathVariable String id) {
        return flightPlannerService.getFlightDetails(id);
    }

    @PostMapping("/testing-api/clear")
    public void clear() {
        flightPlannerService.clearFlights();
    }

    @PutMapping("/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight saveFlight(@Valid @RequestBody Flight flight) {
        flightPlannerService.saveFlight(flight);
        return flight;
    }

    @DeleteMapping("/admin-api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlight(@PathVariable Long id) {
        flightPlannerService.deleteFlight(id);
    }

    @GetMapping("/api/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@RequestParam String search) {
        return flightPlannerService.getAirportDetails(search);
    }


}
