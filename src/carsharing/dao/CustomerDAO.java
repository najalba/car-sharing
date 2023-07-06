package carsharing.dao;

import carsharing.database.DatabaseTaskExecutor;
import carsharing.dto.Customer;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerDAO {
    private static final CustomerDAO INSTANCE = new CustomerDAO();

    private CustomerDAO() {
    }

    public static CustomerDAO getInstance() {
        return INSTANCE;
    }

    public List<Customer> findAll() {
        var databaseExecutor = DatabaseTaskExecutor.getInstance();
        var customers = new ArrayList<Customer>();
        databaseExecutor.execute(connection -> {
            try (var statement = connection.prepareStatement("select * from customer order by id")) {
                var resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    customers.add(new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getObject(3, Integer.class)));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return customers;
    }

    public Customer findById(Integer customerId) {
        var databaseExecutor = DatabaseTaskExecutor.getInstance();
        var customerHolder = new ArrayList<Customer>();
        databaseExecutor.execute(connection -> {
            try (var statement = connection.prepareStatement("select * from customer where id = ?")) {
                statement.setInt(1, customerId);
                var resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    customerHolder.add(new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getObject(3, Integer.class)));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        if (customerHolder.size() > 0) {
            return customerHolder.get(0);
        } else {
            throw new RuntimeException("No customer for id = %d".formatted(customerId));
        }
    }

    public void save(Customer customer) {
        var databaseExecutor = DatabaseTaskExecutor.getInstance();
        if (Objects.isNull(customer.id())) {
            databaseExecutor.execute(connection -> {
                var insert = "insert into customer (name) values (?)";
                try (var preparedStatement = connection.prepareStatement(insert)) {
                    preparedStatement.setString(1, customer.name());
                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            databaseExecutor.execute(connection -> {
                var insert = "update customer set name = ?, rented_car_id = ? where id = ?";
                try (var preparedStatement = connection.prepareStatement(insert)) {
                    preparedStatement.setString(1, customer.name());
                    if (Objects.isNull(customer.carId())) {
                        preparedStatement.setNull(2, JDBCType.INTEGER.getVendorTypeNumber());
                    } else {
                        preparedStatement.setInt(2, customer.carId());
                    }
                    preparedStatement.setInt(3, customer.id());
                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
