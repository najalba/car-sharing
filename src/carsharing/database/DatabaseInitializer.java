package carsharing.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void init(Connection connection) {
        createTables(connection);
    }

    private static void createTables(Connection connection) {
        var company = """
                CREATE TABLE IF NOT EXISTS COMPANY (
                ID INT AUTO_INCREMENT PRIMARY KEY,
                NAME VARCHAR unique not null
                )
                """;
        var car = """
                CREATE TABLE IF NOT EXISTS CAR (
                ID INT AUTO_INCREMENT PRIMARY KEY,
                NAME VARCHAR unique not null,
                COMPANY_ID INT NOT NULL,
                constraint COMPANY_FK FOREIGN KEY (COMPANY_ID) references COMPANY(ID)
                )
                """;
        try (Statement stm = connection.createStatement()) {
            connection.setAutoCommit(true);
            stm.execute(company);
            stm.execute(car);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
