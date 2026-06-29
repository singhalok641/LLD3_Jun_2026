package org.example.parkinglot.strategies.feecalculation;

import org.example.parkinglot.models.Ticket;
import org.example.parkinglot.models.enums.VehicleType;

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
        return 0.0;
    }
}

/*

Hourly rates

id vehicle_type rate

 */
