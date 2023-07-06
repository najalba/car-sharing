package carsharing.companies;

import carsharing.dao.CompanyDAO;
import carsharing.dto.Company;

import java.util.Scanner;

public class CompanyCreateCommand implements CompanyCommand {
    private final Scanner scanner;

    public CompanyCreateCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter the company name:");
        var companyName = this.scanner.nextLine();
        CompanyDAO.getInstance().save(new Company(null, companyName));
        System.out.println("The company was created!");
    }
}
