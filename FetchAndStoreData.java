import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FetchAndStoreData {

    public static void main(String[] args) {
        //String jdbcUrl = "jdbc:mysql://localhost:3306/mydatabase";
		String jdbcUrl = "jdbc:mysql//localhost:3306/mydatabase";
        String username = "root";
        String password = "hello123";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Fetch data from user_login table
            String selectQuery = "SELECT id, login_time, logout_time FROM mydetails";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = selectStatement.executeQuery()) {

                // Store fetched records in user_activity table
                String insertQuery = "INSERT INTO user_activity (id, login_time, logout_time) VALUES (?, ?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    while (resultSet.next()) {
                        String usernameValue = resultSet.getString("id");
                        String loginTimeValue = resultSet.getString("login_time");
                        String logoutTimeValue = resultSet.getString("logout_time");

                        // Set values for the insert statement
                        insertStatement.setString(1, usernameValue);
                        insertStatement.setString(2, loginTimeValue);
                        insertStatement.setString(3, logoutTimeValue);

                        // Execute the insert statement
                        insertStatement.executeUpdate();
                    }
                    System.out.println("Data fetched and stored successfully.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
