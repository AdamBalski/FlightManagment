package pl.flight_managment.v1;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseConnection
{
    Connection connection;
    Statement statement;

    public DataBaseConnection() throws SQLException
    {
        String url = "jdbc:mysql://localhost:3306/FlightManagmentDataBase";
        String user = "java_jdbc_flight_managment";
        String password = "java_jdbc";
        connection = DriverManager.getConnection(url, user, password);

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

    public ArrayList<Flight> getRecords(Flight filter) throws SQLException
    {
        ArrayList<Flight> result = new ArrayList<>();

        for(Flight flight: getRecords())
        {
            if(flight.match(filter))    result.add(flight);
        }

        return result;
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
            int seats = resultSet.getInt("seats");
            int free_seats = resultSet.getInt("free_seats");
            boolean isFavorite = resultSet.getBoolean("is_favorite");
            Flight flight = new Flight(id, aPlace, bPlace, seats, free_seats, isFavorite);

            res.add(flight);
        }

        return res;
    }

    public boolean setFavorite(int id, boolean flag)
    {
        try
        {
            ArrayList<Flight> flights = getRecords();
            for(Flight flight: flights)
            {
                if(flight.getId() == id)
                {
                    statement.executeUpdate(" UPDATE `Flights`" +
                                                " SET is_favorite = " + (flag ? "1" : "0") +
                                                " WHERE id = " + id);
                    return true;
                }
            }

            System.out.println("We can't find a flight with that id.");
        }
        catch(SQLException sqle)
        {
            System.out.println("Something went wrong.");
        }

        System.out.println("Something went wrong.");
        return false;
    }
}
