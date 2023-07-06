package carsharing.commands;

import carsharing.dto.Company;

import java.util.Scanner;

public class CompanyCarCommand implements CarSharingCommand {
    private final Company company;
    private final Scanner scanner;
    private final CompanyCarListCommand companyCarListCommand;
    private final CompanyCarCreateCommand companyCarCreateCommand;
    private final CarSharingCommandExecutor carSharingCommandExecutor = new CarSharingCommandExecutor();

    public CompanyCarCommand(Company company, Scanner scanner) {
        this.company = company;
        this.scanner = scanner;
        this.companyCarListCommand = new CompanyCarListCommand(company);
        this.companyCarCreateCommand = new CompanyCarCreateCommand(company, scanner);
    }

    @Override
    public void execute() {
        System.out.printf("'%s' company:%n", this.company.name());
        boolean running = true;
        while (running) {
            System.out.println("""
                    1. Car list
                    2. Create a car
                    0. Back
                    """);
            var command = this.scanner.nextLine();
            switch (command) {
                case "1" -> {
                    this.carSharingCommandExecutor.setCommand(this.companyCarListCommand);
                    this.carSharingCommandExecutor.execute();
                }
                case "2" -> {
                    this.carSharingCommandExecutor.setCommand(this.companyCarCreateCommand);
                    this.carSharingCommandExecutor.execute();
                }
                case "0" -> running = false;
                default -> System.out.println("No such command");
            }
        }
    }
}
