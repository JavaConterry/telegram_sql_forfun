package StoreDataBot;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServerAccess {
    private static final String INSERT_SQL = "INSERT INTO storage(username, body) VALUES (?,?)";
    public static final String SELECT_ALL_SQL = "SELECT * FROM storage";

    private Connection dataConnection;


    public ServerAccess(){
        try {
            init();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void init() throws SQLException{
        dataConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/datastorage_botDB", "postgres", "0505");
    }

    public void saveToTable(String username, String message) throws SQLException {
        PreparedStatement preparedStatement = dataConnection.prepareStatement(INSERT_SQL);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, message);
        preparedStatement.execute();
    }

    public void showStorageTable() throws SQLException {
        System.out.println(dataConnection.prepareStatement(SELECT_ALL_SQL).execute());
    }



}
