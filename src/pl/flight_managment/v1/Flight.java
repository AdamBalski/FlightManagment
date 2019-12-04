package pl.flight_managment.v1;

public class Flight
{
    int id;

    public Flight(int id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Flight: {id = '" + String.valueOf(id) + "'}";
    }
}
