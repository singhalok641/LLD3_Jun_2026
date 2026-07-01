package org.example.parkinglot.controllers;


import org.example.parkinglot.exceptions.NoAvailableSpotException;
import org.example.parkinglot.models.*;
import org.example.parkinglot.services.TicketService;

public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public Ticket generateTicket(ParkingLot parkingLot, Vehicle vehicle, Gate entryGate) throws NoAvailableSpotException {
        return ticketService.generateTicket(parkingLot, vehicle, entryGate);
    }

    public Bill generateBill(ParkingLot parkingLot, Ticket ticket, Gate exitGate) {
        return ticketService.generateBill(parkingLot, ticket, exitGate);
    }

    public Payment processPayment() {
        return null;
    }
}
