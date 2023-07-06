package carsharing.dao;

import carsharing.database.DatabaseTaskExecutor;
import carsharing.dto.Car;

import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    private static final CarDAO INSTANCE = new CarDAO();

    private CarDAO() {
    }

    public static CarDAO getInstance() {
        return INSTANCE;
    }

    public List<Car> findByCompanyId(Integer companyId) {
        var databaseExecutor = DatabaseTaskExecutor.getInstance();
        var cars = new ArrayList<Car>();
        databaseExecutor.execute(connection -> {
            try (var statement = connection.prepareStatement("select * from car where company_id = ? order by id")) {
                statement.setInt(1, companyId);
                var resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    cars.add(new Car(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return cars;
    }

    public void save(Car car) {
        var databaseExecutor = DatabaseTaskExecutor.getInstance();
        databaseExecutor.execute(connection -> {
            var insert = "insert into car (name, company_id) values (?, ?)";
            try (var preparedStatement = connection.prepareStatement(insert)) {
                preparedStatement.setString(1, car.name());
                preparedStatement.setInt(2, car.companyId());
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
