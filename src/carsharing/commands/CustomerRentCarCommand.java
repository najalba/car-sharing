package carsharing.commands;

import carsharing.dao.CarDAO;
import carsharing.dao.CompanyDAO;
import carsharing.dao.CustomerDAO;
import carsharing.dto.Company;
import carsharing.dto.Customer;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class CustomerRentCarCommand implements CarSharingCommand {
    private final Customer customer;
    private final Scanner scanner;

    public CustomerRentCarCommand(Customer customer, Scanner scanner) {
        this.customer = customer;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        var customer = CustomerDAO.getInstance().findById(this.customer.id());
        if (Objects.nonNull(customer.carId())) {
            System.out.println("You've already rented a car!");
        } else {
            var companyOptional = this.chooseCompany();
            companyOptional.ifPresentOrElse(this::chooseCar, () -> System.out.println("The company list is empty!"));
        }
    }

    private Optional<Company> chooseCompany() {
        var companies = CompanyDAO.getInstance().findAll();
        System.out.println("Choose a company:");
        companies.forEach(c -> System.out.printf("%d. %s%n", c.id(), c.name()));
        System.out.println("0. Back");
        var option = this.scanner.nextLine();
        return companies.stream().filter(c -> option.equals(c.id().toString())).findAny();
    }

    private void chooseCar(Company company) {
        var availableCars = CarDAO.getInstance().findAvailableByCompanyId(company.id());
        if (availableCars.size() == 0) {
            System.out.printf("No available cars in the '%s' company%n", company.name());
        } else {
            System.out.println("Choose a car:");
            for (int i = 0; i < availableCars.size(); i++) {
                System.out.println((i + 1) + ". " + availableCars.get(i).name());
            }
            System.out.println("0. Back");
            var option = this.scanner.nextLine();
            if (!"0".equals(option)) {
                var car = availableCars.get(Integer.parseInt(option) - 1);
                var newCustomer = new Customer(this.customer.id(), this.customer.name(), car.id());
                CustomerDAO.getInstance().save(newCustomer);
                System.out.printf("You rented '%s'%n", car.name());
            }
        }
    }
}
