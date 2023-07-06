package carsharing.commands;

import carsharing.dao.CustomerDAO;
import carsharing.dto.Customer;

import java.util.Objects;

public class CustomerReturnCarCommand implements CarSharingCommand {
    private final Customer customer;

    public CustomerReturnCarCommand(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void execute() {
        var customer = CustomerDAO.getInstance().findById(this.customer.id());
        if (Objects.isNull(customer.carId())) {
            System.out.println("You didn't rent a car!");
        } else {
            var newCustomer = new Customer(this.customer.id(), this.customer.name(), null);
            CustomerDAO.getInstance().save(newCustomer);
            System.out.println("You've returned a rented car!");
        }
    }
}
