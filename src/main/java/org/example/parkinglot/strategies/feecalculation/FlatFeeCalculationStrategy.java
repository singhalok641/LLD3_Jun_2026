package org.example.parkinglot.strategies.feecalculation;

import org.example.parkinglot.models.Ticket;

public class FlatFeeCalculationStrategy implements FeeCalculationStrategy {
    @Override
    public double calculateFee(Ticket ticket) {
        // TODO:: Implement me.
        // A flat rate of ₹100 for any vehicle regardless of duration.
        // Plug it into the parking lot and test.
        return 0;
    }
}
