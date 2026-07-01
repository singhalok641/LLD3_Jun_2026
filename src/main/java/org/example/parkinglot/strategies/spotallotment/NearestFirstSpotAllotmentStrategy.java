package org.example.parkinglot.strategies.spotallotment;

import org.example.parkinglot.models.ParkingFloor;
import org.example.parkinglot.models.ParkingSpot;
import org.example.parkinglot.models.enums.ParkingFloorStatus;
import org.example.parkinglot.models.enums.ParkingSpotStatus;
import org.example.parkinglot.models.enums.VehicleType;

import java.util.List;
import java.util.Optional;

public class NearestFirstSpotAllotmentStrategy implements SpotAllotmentStrategy {

    @Override
    public Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, VehicleType vehicleType) {
        // Iterate floors from lowest to highest
        for (ParkingFloor floor : floors) {
            if (floor.getStatus() != ParkingFloorStatus.OPERATIONAL) {
                continue;
            }

            // Find the first available spot matching the vehicle type
            for (ParkingSpot spot : floor.getParkingSpots()) {
                if (spot.getStatus() == ParkingSpotStatus.AVAILABLE
                        && spot.getVehicleType() == vehicleType) {
                    return Optional.of(spot);
                }
            }
        }

        return null; // No spot available
    }
}
