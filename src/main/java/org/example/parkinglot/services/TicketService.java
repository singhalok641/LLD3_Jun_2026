package org.example.parkinglot.services;

import org.example.parkinglot.exceptions.NoAvailableSpotException;
import org.example.parkinglot.models.*;
import org.example.parkinglot.models.enums.ParkingSpotStatus;
import org.example.parkinglot.models.enums.VehicleType;
import org.example.parkinglot.strategies.feecalculation.FeeCalculationStrategy;
import org.example.parkinglot.strategies.spotallotment.SpotAllotmentStrategy;

import java.sql.SQLOutput;
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

        System.out.println("Ticket generated: " + ticketNumber);
        System.out.println(" Vehicle: " + vehicle.getLicensePlate());
        System.out.println(" Spot: Floor " + parkingSpot.getParkingFloor().getFloorNumber() + "" +
                ", Spot " + parkingSpot.getSpotNumber());
        System.out.println();

        return ticket;
    }

    // EXIT: generateBill
    public Bill generateBill(ParkingLot parkingLot, Ticket ticket, Gate exitGate) {
        // Step 1: Calculate the fee using the fee calculation strategy
        FeeCalculationStrategy feeCalculationStrategy = parkingLot.getFeeCalculationStrategy();
        Long amount = feeCalculationStrategy.calculateFee(ticket);

        // Step 2: Create a Bill Object
        Bill bill = new Bill(
               System.nanoTime(),
               ticket,
               exitGate,
               ticket.getOperator(),
               amount
        );

        // Step 3: Free the parking spot
        ticket.getParkingSpot().freeSpot();

        System.out.println("Bill generated: ");
        System.out.println(" Ticket: " + ticket.getTicketNumber());
        System.out.println(" Vehicle: " + ticket.getVehicle().getLicensePlate());
        System.out.println(" Amount: Rs. " + String.format("%.2f", 1.0 * amount / 100));
        System.out.println(" Status: " + bill.getStatus());
        System.out.println();

        return bill;
    }

    // processPayment

    //
}
