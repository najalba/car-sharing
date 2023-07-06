package carsharing;

import carsharing.commands.*;
import carsharing.database.DatabaseInitializer;
import carsharing.database.DatabaseTaskExecutor;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        DatabaseTaskExecutor.init(databaseName(args)).execute(DatabaseInitializer::init);
        var scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("""
                    1. Log in as a manager
                    2. Log in as a customer
                    3. Create a customer
                    0. Exit
                    """);
            var level1Command = scanner.nextLine();
            switch (level1Command) {
                case "1" -> managerCommand(scanner);
                case "2" -> customerCommand(scanner);
                case "3" -> createCustomerCommand(scanner);
                case "0" -> running = false;
                default -> System.out.println("No such command!");
            }
        }
    }

    private static String databaseName(String[] args) {
        var arguments = Arrays.asList(args);
        var dbNameIndex = arguments.indexOf("-databaseFileName");
        return dbNameIndex > -1 && arguments.size() >= dbNameIndex + 2 ? arguments.get(dbNameIndex + 1) : UUID.randomUUID().toString();
    }

    private static void managerCommand(Scanner scanner) {
        var companyListCommand = new CompanyListCommand(scanner);
        var companyCreateCommand = new CompanyCreateCommand(scanner);
        var companyCommandExecutor = new CarSharingCommandExecutor();
        boolean running = true;
        while (running) {
            System.out.println("""                                        
                    1. Company list
                    2. Create a company
                    0. Back
                    """);
            var leve2Command = scanner.nextLine();
            switch (leve2Command) {
                case "1" -> companyCommandExecutor.setCommand(companyListCommand);
                case "2" -> companyCommandExecutor.setCommand(companyCreateCommand);
                case "0" -> running = false;
                default -> System.out.println("No such command");
            }
            if (running) {
                companyCommandExecutor.execute();
            }
        }
    }

    private static void createCustomerCommand(Scanner scanner) {
        var customerCreateCommand = new CustomerCreateCommand(scanner);
        var carSharingCommandExecutor = new CarSharingCommandExecutor();
        carSharingCommandExecutor.setCommand(customerCreateCommand);
        carSharingCommandExecutor.execute();
    }

    private static void customerCommand(Scanner scanner) {
        var customerListCommand = new CustomerListCommand(scanner);
        var carSharingCommandExecutor = new CarSharingCommandExecutor();
        carSharingCommandExecutor.setCommand(customerListCommand);
        carSharingCommandExecutor.execute();
    }
}
