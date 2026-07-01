package org.example.parkinglot.strategies.spotallotment;

import org.example.parkinglot.models.ParkingFloor;
import org.example.parkinglot.models.ParkingSpot;
import org.example.parkinglot.models.enums.VehicleType;

import java.util.List;
import java.util.Optional;

public class RandomSpotAllotmentStrategy implements SpotAllotmentStrategy {
    @Override
    public Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, VehicleType vehicleType) {
        // TODO:: Implement me
        // Instead of nearest-first, pick a random available spot.
        // Test that it works correctly.
        return Optional.empty();
    }
}
