package pl.flight_managment.v1;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseConnection
{
    Connection connection;
    Statement statement;

    public DataBaseConnection() throws SQLException
    {
        connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/FlightManagmentDataBase",
                        "java_jdbc_flight_managment", "java_jdbc");

        statement = connection.createStatement();
    }

    public ArrayList<Flight> getRecords() throws SQLException
    {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Flights");

        ArrayList<Flight> res = new ArrayList<>();

        while(resultSet.next())
        {
            int id = resultSet.getInt(1);

            Flight flight = new Flight(id);

            res.add(flight);
        }

        return res;
    }
}
