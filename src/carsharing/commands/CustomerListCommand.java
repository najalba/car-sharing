package carsharing.commands;

import carsharing.dao.CustomerDAO;

import java.util.Scanner;

public class CustomerListCommand implements CarSharingCommand {
    private final Scanner scanner;
    private final CarSharingCommandExecutor carSharingCommandExecutor = new CarSharingCommandExecutor();

    public CustomerListCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        var customerDao = CustomerDAO.getInstance();
        var customersList = customerDao.findAll();
        if (customersList.size() > 0) {
            System.out.println("Customer list:");
            customersList.forEach(c -> System.out.printf("%d. %s%n", c.id(), c.name()));
            System.out.println("0. Back");
            var option = this.scanner.nextLine();
            if (!"0".equals(option)) {
                var customer = customersList.stream().filter(c -> option.equals(c.id().toString())).findAny();
                customer.ifPresent(c -> {
                    this.carSharingCommandExecutor.setCommand(new CustomerCarCommand(c, scanner));
                    this.carSharingCommandExecutor.execute();
                });
            }
        } else {
            System.out.println("The customer list is empty!");
        }
    }
}
