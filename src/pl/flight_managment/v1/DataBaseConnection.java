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

    public ArrayList<Flight> getRecords(Flight filter, DateRange dateRange) throws SQLException
    {
        ArrayList<Flight> result = new ArrayList<>();

        for(Flight flight: getRecords())
        {

            if(flight.match(filter))// && flight.date.matches(dateRange))
                if(flight.date.matches(dateRange))
                result.add(flight);
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
            Date date = new Date(resultSet.getString("date"));
            int seats = resultSet.getInt("seats");
            int free_seats = resultSet.getInt("free_seats");
            boolean isFavorite = resultSet.getBoolean("is_favorite");
            Flight flight = new Flight(id, aPlace, bPlace, date, seats, free_seats, isFavorite);

            res.add(flight);
        }

        return res;
    }

    public void setFavorite(int id, boolean flag)
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
                    return;
                }
            }

            System.out.println("We can't find a flight with that id.");
            return;
        }
        catch(SQLException sqle)
        {
            System.out.println("Something went wrong with data base.");
        }

        System.out.println("Something went wrong.");
    }
}
