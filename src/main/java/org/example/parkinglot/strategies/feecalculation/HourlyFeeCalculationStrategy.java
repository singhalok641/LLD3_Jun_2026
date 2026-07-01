package org.example.parkinglot.strategies.feecalculation;

import org.example.parkinglot.models.Ticket;
import org.example.parkinglot.models.enums.VehicleType;

import java.util.Date;
import java.util.Map;

public class HourlyFeeCalculationStrategy implements FeeCalculationStrategy {
    // Rate per hour for each vehicle type
    private final Map<VehicleType, Long> hourlyRates = Map.of(
            VehicleType.TWO_WHEELER, 2000L,
            VehicleType.FOUR_WHEELER, 4000L,
            VehicleType.LARGE, 6000L
    );

    @Override
    public Long calculateFee(Ticket ticket) {
        Date entryTime = ticket.getEntryTime();
        Date exitTime = new Date(); // current time

        // Calculate duration in hours (round up)
        long durationMillis = exitTime.getTime() - entryTime.getTime();
        long durationHours = (long) Math.ceil(durationMillis / (1000.0 * 60 * 60)) + 1;

        // Minimum 1 hour
        durationHours = Math.max(1, durationHours);

        VehicleType vehicleType = ticket.getVehicle().getVehicleType();
        Long rate = hourlyRates.getOrDefault(vehicleType, 4000L);

        return rate * durationHours;
    }
}

/*

Hourly rates

id vehicle_type rate

 */
