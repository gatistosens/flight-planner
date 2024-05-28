package io.codelex.flightplanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightPlannerModeConfiguration {
    @Value("${myapp.flight-planner-storage-mode}")
    private String storageMode;

    @Bean
    public FlightPlannerService createFlightPlannerServiceBean(FlightPlannerRepository flightPlannerRepository) {
        if ("database".equals(storageMode)) {
            return new FlightPlannerDBService(flightPlannerRepository);
        } else {
            return new FlightPlannerInMemoryService(flightPlannerRepository);
        }
    }
}
