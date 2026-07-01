package org.example.parkinglot.models;


import org.example.parkinglot.exceptions.InvalidParkingLotException;
import org.example.parkinglot.models.enums.GateType;
import org.example.parkinglot.models.enums.ParkingLotStatus;
import org.example.parkinglot.models.enums.VehicleType;
import org.example.parkinglot.strategies.feecalculation.FeeCalculationStrategy;
import org.example.parkinglot.strategies.spotallotment.SpotAllotmentStrategy;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private Long id;
    private String name;
    private String address;
    private List<ParkingFloor> parkingFloors;
    private List<Gate> gates;
    private ParkingLotStatus status;
    private List<VehicleType> allowedVehicleTypes;
    private SpotAllotmentStrategy allotmentStrategy;
    private FeeCalculationStrategy feeCalculationStrategy;

    // private constructor - only Builder can create a ParkingLot
    private ParkingLot(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
        this.parkingFloors = builder.parkingFloors;
        this.gates = builder.gates;
        this.status = ParkingLotStatus.OPERATIONAL;
        this.allowedVehicleTypes = builder.allowedVehicleTypes;
        this.allotmentStrategy = builder.allotmentStrategy;
        this.feeCalculationStrategy = builder.feeCalculationStrategy;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public List<ParkingFloor> getParkingFloors() { return parkingFloors; }
    public List<Gate> getGates() { return gates; }
    public ParkingLotStatus getStatus() { return status; }
    public List<VehicleType> getAllowedVehicleTypes() { return allowedVehicleTypes; }
    public SpotAllotmentStrategy getAllotmentStrategy() { return allotmentStrategy; }
    public FeeCalculationStrategy getFeeCalculationStrategy() { return feeCalculationStrategy; }

    // Setters
    public void setStatus(ParkingLotStatus status) { this.status = status; }

    public void addParkingFloor(ParkingFloor floor) {
        parkingFloors.add(floor);
    }

    public void addGate(Gate gate) {
        gates.add(gate);
    }

    public void addAllowedVehicleType(VehicleType type) {
        allowedVehicleTypes.add(type);
    }

    public static class Builder {
        private Long id;
        private String name;
        private String address;
        private List<ParkingFloor> parkingFloors;
        private List<Gate> gates;
        private ParkingLotStatus status;
        private List<VehicleType> allowedVehicleTypes;
        private SpotAllotmentStrategy allotmentStrategy;
        private FeeCalculationStrategy feeCalculationStrategy;

        public Builder() {
            this.parkingFloors = new ArrayList<>();
            this.gates = new ArrayList<>();
            this.allowedVehicleTypes = new ArrayList<>();
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setGates(List<Gate> gates) {
            this.gates = gates;
            return this;
        }

        public Builder setParkingFloors(List<ParkingFloor> parkingFloors) {
            this.parkingFloors = parkingFloors;
            return this;
        }

        public Builder setAllowedVehicleTypes(List<VehicleType> allowedVehicleTypes) {
            this.allowedVehicleTypes = allowedVehicleTypes;
            return this;
        }

        public Builder setAllotmentStrategy(SpotAllotmentStrategy allotmentStrategy) {
            this.allotmentStrategy = allotmentStrategy;
            return this;
        }

        public Builder setFeeCalculationStrategy(FeeCalculationStrategy feeCalculationStrategy) {
            this.feeCalculationStrategy = feeCalculationStrategy;
            return this;
        }

        // validations before building the parking lot object
        private void validate() throws InvalidParkingLotException {
            if (parkingFloors.isEmpty()) {
                throw new InvalidParkingLotException(
                        "Parking lot must have at least one parking floor"
                );
            }

            boolean hasEntryGate = gates.stream().anyMatch(g -> g.getGateType() == GateType.ENTRY);
            if (!hasEntryGate) {
                throw new InvalidParkingLotException(
                        "Parking Lot must have at least one entry gate"
                );
            }

            boolean hasExitGate = gates.stream().anyMatch(g -> g.getGateType() == GateType.EXIT);
            if (!hasExitGate) {
                throw new InvalidParkingLotException(
                        "Parking Lot must have at least one exit gate"
                );
            }

            if (allotmentStrategy == null) {
                throw new InvalidParkingLotException(
                        "Spot allotment strategy must be configured"
                );
            }

            if (feeCalculationStrategy == null) {
                throw new InvalidParkingLotException(
                        "Fee Calculation strategy must be configured"
                );
            }
        }

        public ParkingLot build() throws InvalidParkingLotException {
            validate();
            return new ParkingLot(this);
        }
    }
}


/*
Validations?
1. At least one parking floor and atleast one parking spot one any of the floors
2. At least one entry gate and one exit gate
3. At least allow one vehicle type
4. strategy for allotment of the parking spot
5. fee calculation strategy must be configured for this parking lot.
 */

