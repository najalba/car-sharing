package carsharing.companies;

import carsharing.dao.CompanyDAO;

public class CompanyListCommand implements CompanyCommand {
    @Override
    public void execute() {
        var companyDao = CompanyDAO.getInstance();
        var companies = companyDao.findAll();
        if (companies.size() > 0) {
            System.out.println("Company list:");
            companies.forEach(c -> System.out.printf("%d. %s%n", c.id(), c.name()));
        } else {
            System.out.println("The company list is empty!");
        }
    }
}
