package carsharing.commands;

import carsharing.dao.CompanyDAO;

import java.util.Scanner;

public class CompanyListCommand implements CarSharingCommand {
    private final Scanner scanner;
    private final CarSharingCommandExecutor carSharingCommandExecutor = new CarSharingCommandExecutor();

    public CompanyListCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        var companyDao = CompanyDAO.getInstance();
        var companies = companyDao.findAll();
        if (companies.size() > 0) {
            System.out.println("Choose a company:");
            companies.forEach(c -> System.out.printf("%d. %s%n", c.id(), c.name()));
            System.out.println("0. Back");
            var option = this.scanner.nextLine();
            if (!"0".equals(option)) {
                var company = companies.stream().filter(c -> option.equals(c.id().toString())).findAny();
                company.ifPresent(c -> {
                    this.carSharingCommandExecutor.setCommand(new CompanyCarCommand(c, this.scanner));
                    this.carSharingCommandExecutor.execute();
                });
            }
        } else {
            System.out.println("The company list is empty!");
        }
    }
}
