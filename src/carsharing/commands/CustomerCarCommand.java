package carsharing.commands;

import carsharing.dto.Customer;

import java.util.Scanner;

public class CustomerCarCommand implements CarSharingCommand {
    private final Scanner scanner;
    private final CarSharingCommandExecutor carSharingCommandExecutor;
    private final CustomerRentCarCommand customerRentCarCommand;
    private final CustomerReturnCarCommand customerReturnCarCommand;
    private final CustomerRentedCarCommand customerRentedCarCommand;

    public CustomerCarCommand(Customer customer, Scanner scanner) {
        this.scanner = scanner;
        this.carSharingCommandExecutor = new CarSharingCommandExecutor();
        this.customerRentCarCommand = new CustomerRentCarCommand(customer, scanner);
        this.customerReturnCarCommand = new CustomerReturnCarCommand(customer);
        this.customerRentedCarCommand = new CustomerRentedCarCommand(customer);
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            System.out.println("""
                    1. Rent a car
                    2. Return a rented car
                    3. My rented car
                    0. Back
                    """);
            var command = this.scanner.nextLine();
            switch (command) {
                case "1" -> this.carSharingCommandExecutor.setCommand(this.customerRentCarCommand);
                case "2" -> this.carSharingCommandExecutor.setCommand(this.customerReturnCarCommand);
                case "3" -> this.carSharingCommandExecutor.setCommand(this.customerRentedCarCommand);
                case "0" -> running = false;
                default -> System.out.println("No such command");
            }
            if (running) {
                this.carSharingCommandExecutor.execute();
            }
        }
    }
}
