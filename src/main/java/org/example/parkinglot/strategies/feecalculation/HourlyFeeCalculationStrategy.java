package org.example.parkinglot.strategies.feecalculation;

import org.example.parkinglot.models.Ticket;
import org.example.parkinglot.models.enums.VehicleType;

import java.util.Date;
import java.util.Map;

public class HourlyFeeCalculationStrategy implements FeeCalculationStrategy {
    // Rate per hour for each vehicle type
    private final Map<VehicleType, Double> hourlyRates;

    public HourlyFeeCalculationStrategy(Map<VehicleType, Double> hourlyRates) {
        this.hourlyRates = Map.of(
                VehicleType.TWO_WHEELER, 20.0,
                VehicleType.FOUR_WHEELER, 40.0,
                VehicleType.LARGE, 60.0
        );
    }

    @Override
    public double calculateFee(Ticket ticket) {
        Date entryTime = ticket.getEntryTime();
        Date exitTime = new Date(); // current time

        // Calculate duration in hours (round up)
        long durationMillis = exitTime.getTime() - entryTime.getTime();
        long durationHours = (long) Math.ceil(durationMillis / (1000.0 * 60 * 60));

        // Minimum 1 hour
        durationHours = Math.max(1, durationHours);

        VehicleType vehicleType = ticket.getVehicle().getVehicleType();
        double rate = hourlyRates.getOrDefault(vehicleType, 40.0);

        return rate * durationHours;
    }
}

/*

Hourly rates

id vehicle_type rate

 */
