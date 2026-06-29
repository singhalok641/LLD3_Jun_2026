package org.example.parkinglot.services;

import org.example.parkinglot.exceptions.NoAvailableSpotException;
import org.example.parkinglot.models.*;
import org.example.parkinglot.models.enums.ParkingSpotStatus;
import org.example.parkinglot.models.enums.VehicleType;
import org.example.parkinglot.strategies.spotallotment.SpotAllotmentStrategy;

import java.util.Optional;
import java.util.UUID;

public class TicketService {
    // generate ticket
    public Ticket generateTicket(ParkingLot parkingLot, Vehicle vehicle, Gate entryGate) throws NoAvailableSpotException {
        // Step 1: Find an available spot using the allotment strategy
        SpotAllotmentStrategy spotAllotmentStrategy = parkingLot.getAllotmentStrategy();
        Optional<ParkingSpot> parkingSpotOptional = spotAllotmentStrategy.findSpot(parkingLot.getParkingFloors(), vehicle.getVehicleType());

        if (parkingSpotOptional.isEmpty()) {
            throw new NoAvailableSpotException(
                    "No available spot for the vehicle type: " + vehicle.getVehicleType()
            );
        }

        ParkingSpot parkingSpot = parkingSpotOptional.get();

        // Step 2: Assign the vehicle to the spot
        parkingSpot.assignVehicle(vehicle);

        // Step 3: Create a ticket object;

        String ticketNumber = "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Operator operator = entryGate.getOperator();

        Ticket ticket = new Ticket(
                System.nanoTime(),
                ticketNumber,
                vehicle,
                parkingSpot,
                entryGate,
                operator
        );

        return ticket;
    }

    // generateBill

    // processPayment

    //
}
