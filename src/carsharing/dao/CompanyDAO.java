package carsharing.dao;

import carsharing.database.DatabaseTaskExecutor;
import carsharing.dto.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {
    private static final CompanyDAO INSTANCE = new CompanyDAO();

    private CompanyDAO() {
    }

    public static CompanyDAO getInstance() {
        return INSTANCE;
    }

    public List<Company> findAll() {
        var databaseExecutor = DatabaseTaskExecutor.getInstance();
        var companies = new ArrayList<Company>();
        databaseExecutor.execute(connection -> {
            try (var statement = connection.prepareStatement("select * from company order by id")) {
                var resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    companies.add(new Company(resultSet.getInt(1), resultSet.getString(2)));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return companies;
    }

    public Company findById(Integer id) {
        var databaseExecutor = DatabaseTaskExecutor.getInstance();
        var companyHolder = new ArrayList<Company>();
        databaseExecutor.execute(connection -> {
            try (var statement = connection.prepareStatement("select * from company where id = ?")) {
                statement.setInt(1, id);
                var resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    companyHolder.add(new Company(resultSet.getInt(1), resultSet.getString(2)));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        if (companyHolder.size() > 0) {
            return companyHolder.get(0);
        } else {
            throw new IllegalArgumentException("No company found for ID = %d".formatted(id));
        }
    }


    public void save(Company company) {
        var databaseExecutor = DatabaseTaskExecutor.getInstance();
        databaseExecutor.execute(connection -> {
            var insert = "insert into company (name) values (?)";
            try (var preparedStatement = connection.prepareStatement(insert)) {
                preparedStatement.setString(1, company.name());
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
