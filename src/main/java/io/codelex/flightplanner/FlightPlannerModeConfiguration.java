package io.codelex.flightplanner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightPlannerModeConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "myapp", name = "flight-planner-storage-mode", havingValue = "database")
    public FlightPlannerService createFlightPlannerDBService(FlightPlannerDBRepository flightPlannerDBRepository, AirportRepository airportRepository) {
        return new FlightPlannerDBService(flightPlannerDBRepository, airportRepository);
    }

    @Bean
    @ConditionalOnProperty(prefix = "myapp", name = "flight-planner-storage-mode", havingValue = "memory")
    public FlightPlannerService createFlightPlannerInMemoryService(FlightPlannerInMemoryRepository flightPlannerInMemoryRepository) {
        return new FlightPlannerInMemoryService(flightPlannerInMemoryRepository);
    }
}
