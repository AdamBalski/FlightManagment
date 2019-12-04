package pl.flight_managment.v1;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws SQLException
    {
        DataBaseConnection dbc = new DataBaseConnection();
        ArrayList<Flight> l = dbc.getRecords();


        for(Flight f: l)
        {
            System.out.println(f.toString());
        }
    }
}
