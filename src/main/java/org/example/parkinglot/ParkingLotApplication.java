package org.example.parkinglot;

import org.example.parkinglot.controllers.TicketController;
import org.example.parkinglot.models.*;
import org.example.parkinglot.models.enums.GateType;
import org.example.parkinglot.models.enums.VehicleType;
import org.example.parkinglot.services.TicketService;
import org.example.parkinglot.strategies.feecalculation.HourlyFeeCalculationStrategy;
import org.example.parkinglot.strategies.spotallotment.NearestFirstSpotAllotmentStrategy;

import java.util.List;
import java.util.SortedMap;

public class ParkingLotApplication {
    public static void main(String[] args) throws Exception {
        // Setup components

        // 1. Create parking floors with spots
        ParkingFloor floor1 = new ParkingFloor(1L, 1, 500);
        floor1.addParkingSpot(new ParkingSpot(101L, 1, VehicleType.TWO_WHEELER));
        floor1.addParkingSpot(new ParkingSpot(102L, 2, VehicleType.TWO_WHEELER));
        floor1.addParkingSpot(new ParkingSpot(103L, 3, VehicleType.FOUR_WHEELER));
        floor1.addParkingSpot(new ParkingSpot(104L, 4, VehicleType.FOUR_WHEELER));
        floor1.addParkingSpot(new ParkingSpot(105L, 5, VehicleType.FOUR_WHEELER));

        ParkingFloor floor2 = new ParkingFloor(2L, 2, 100);
        floor2.addParkingSpot(new ParkingSpot(201L, 1, VehicleType.FOUR_WHEELER));
        floor2.addParkingSpot(new ParkingSpot(202L, 2, VehicleType.FOUR_WHEELER));
        floor2.addParkingSpot(new ParkingSpot(203L, 3, VehicleType.LARGE));
        floor2.addParkingSpot(new ParkingSpot(204L, 4, VehicleType.LARGE));

        // 2. Create Operators
        Operator op1 = new Operator(1L, "EMP001", "Rajesh");
        Operator op2 = new Operator(2L, "EMP002", "Alok");

        // 3. Create Gates
        Gate entryGate = new Gate(1L, 1, GateType.ENTRY, op1);
        Gate exitGate = new Gate(2L, 2, GateType.EXIT, op2);

        // Builder pattern to create a Parking Lot
        ParkingLot parkingLot = ParkingLot.getBuilder()
                .setId(1L)
                .setName("Airport Parking")
                .setAddress("Terminal 3, IGI Airport")
                .setParkingFloors(List.of(floor1, floor2))
                .setGates(List.of(entryGate, exitGate))
                .setAllowedVehicleTypes(List.of(VehicleType.TWO_WHEELER, VehicleType.FOUR_WHEELER, VehicleType.LARGE))
                .setAllotmentStrategy(new NearestFirstSpotAllotmentStrategy())
                .setFeeCalculationStrategy(new HourlyFeeCalculationStrategy())
                .build();

        // Create a controller
        TicketController controller = new TicketController(new TicketService());

        System.out.println("======================================");
        System.out.println("  Welcome to " + parkingLot.getName());
        System.out.println("======================================");
        System.out.println();

        // Scenario 1: Car enters and parks
        System.out.println("-- Scenario 1: Car enters --");
        Vehicle car1 = new Vehicle(1L, "DL-01-AB-5678", VehicleType.FOUR_WHEELER, "Sreeraj");
        Ticket ticket1 = controller.generateTicket(parkingLot, car1, entryGate);

        // Scenario 2: Bike enters and parks
        System.out.println("-- Scenario 2: Bike enters --");
        Vehicle bike1 = new Vehicle(2L, "DL-01-CD-1234", VehicleType.TWO_WHEELER, "Haswin");
        Ticket ticket2 = controller.generateTicket(parkingLot, bike1, entryGate);

        // Scenario 3: Another car enters
        System.out.println("-- Scenario 3: Another car enters --");
        Vehicle car2 = new Vehicle(3L, "DL-01-CD-5678", VehicleType.FOUR_WHEELER, "Harsh");
        Ticket ticket3 = controller.generateTicket(parkingLot, car2, entryGate);

        // Scenario 4: Car 1 exits - full payment in cash
        System.out.println("- Scenario 4: Car 1 exits (full cash payment) --");
        Bill bill1 = controller.generateBill(parkingLot, ticket1, exitGate);
        controller.processPayment();

        // Scenario 5: Bike exists - partial payment (cash + online)

        // Scenario 6: Truck enters

    }
}
