package carsharing.commands;

import carsharing.dao.CustomerDAO;
import carsharing.dto.Customer;

import java.util.Scanner;

public class CustomerCreateCommand implements CarSharingCommand {
    private final Scanner scanner;

    public CustomerCreateCommand(Scanner scanner) {
        this.scanner = scanner;
    }


    @Override
    public void execute() {
        System.out.println("Enter the customer name:");
        var customerName = this.scanner.nextLine();
        CustomerDAO.getInstance().save(new Customer(null, customerName, null));
        System.out.println("The customer was added!");
    }
}
