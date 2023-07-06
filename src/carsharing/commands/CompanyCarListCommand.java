package carsharing.commands;

import carsharing.dao.CarDAO;
import carsharing.dto.Company;

public class CompanyCarListCommand implements CarSharingCommand {
    private final Company company;

    public CompanyCarListCommand(Company company) {
        this.company = company;
    }

    @Override
    public void execute() {
        var carDao = CarDAO.getInstance();
        var cars = carDao.findByCompanyId(this.company.id());
        if (cars.size() > 0) {
            System.out.println("Car list:");
            for (int i = 0; i < cars.size(); i++) {
                System.out.println((i + 1) + ". " + cars.get(i).name());
            }
            System.out.println();
        } else {
            System.out.println("The car list is empty!");
        }
    }
}
