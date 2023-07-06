package carsharing.database;

import java.sql.Connection;

public interface DatabaseTask {
    void execute(Connection connection);
}
