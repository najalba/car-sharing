package carsharing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class DatabaseTaskExecutor {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL_PREFIX = "jdbc:h2:./src/carsharing/db/";
    private static DatabaseTaskExecutor INSTANCE;
    private final String dbUrl;

    private DatabaseTaskExecutor(String databaseName) {
        this.dbUrl = DB_URL_PREFIX + databaseName;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized DatabaseTaskExecutor init(String databaseName) {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new DatabaseTaskExecutor(databaseName);
            return INSTANCE;
        } else {
            throw new IllegalStateException("Database already initialized!");
        }
    }

    public static DatabaseTaskExecutor getInstance() {
        if (Objects.isNull(INSTANCE)) {
            throw new IllegalStateException("Database isn't initialized!");
        }
        return INSTANCE;
    }

    public void execute(DatabaseTask databaseTask) {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            databaseTask.execute(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
