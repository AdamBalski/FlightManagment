package pl.flight_managment.v1;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseConnection
{
    Connection connection;
    Statement statement;

    public DataBaseConnection() throws SQLException
    {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FlightManagmentDataBase", "java_jdbc_flight_managment", "java_jdbc");

        statement = connection.createStatement();
    }

    public boolean bookAFlight(int id) throws SQLException
    {
        ResultSet resultSet = statement.executeQuery("SELECT free_seats FROM Flights WHERE id = " + id);

        resultSet.next();
        int free_seats = resultSet.getInt("free_seats");

        if(free_seats < 1)
        {
            return false;
        }
        else
        {
            free_seats -= 1;
            statement.executeUpdate(" UPDATE `Flights`" +
                                         " SET free_seats = " + free_seats +
                                         " WHERE id = " + id);

            return true;
        }
    }

    public ArrayList<Flight> getRecords() throws SQLException
    {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Flights");

        ArrayList<Flight> res = new ArrayList<>();

        while(resultSet.next())
        {
            int id = resultSet.getInt("id");
            String aPlace = resultSet.getString("a_place");
            String bPlace = resultSet.getString("b_place");
            Flight flight = new Flight(id, aPlace, bPlace);

            res.add(flight);
        }

        return res;
    }
}
