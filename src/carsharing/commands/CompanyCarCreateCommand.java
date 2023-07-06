package carsharing.commands;

import carsharing.dao.CarDAO;
import carsharing.dto.Car;
import carsharing.dto.Company;

import java.util.Scanner;

public class CompanyCarCreateCommand implements CarSharingCommand {
    private final Company company;
    private final Scanner scanner;

    public CompanyCarCreateCommand(Company company, Scanner scanner) {
        this.company = company;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        var carDao = CarDAO.getInstance();
        System.out.println("Enter the car name:");
        var carName = this.scanner.nextLine();
        var car = new Car(null, carName, this.company.id());
        carDao.save(car);
        System.out.println("The car was added!");
    }
}
