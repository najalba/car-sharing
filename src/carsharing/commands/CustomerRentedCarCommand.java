package carsharing.commands;

import carsharing.dao.CarDAO;
import carsharing.dao.CompanyDAO;
import carsharing.dao.CustomerDAO;
import carsharing.dto.Customer;

import java.util.Objects;

public class CustomerRentedCarCommand implements CarSharingCommand {
    private final Customer customer;

    public CustomerRentedCarCommand(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void execute() {
        var customer = CustomerDAO.getInstance().findById(this.customer.id());
        if (Objects.isNull(customer.carId())) {
            System.out.println("You didn't rent a car!");
        } else {
            var car = CarDAO.getInstance().findById(customer.carId());
            var company = CompanyDAO.getInstance().findById(car.companyId());
            System.out.println("Your rented car:");
            System.out.println(car.name());
            System.out.println("Company:");
            System.out.println(company.name());
        }
    }
}
