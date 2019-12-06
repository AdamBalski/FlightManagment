package pl.flight_managment.v1;

public class Flight
{
    int id;
    String aPlace;
    String bPlace;
    String seats;
    String free_seats;

    public Flight(int id, String aPlace, String bPlace)
    {
        this.id = id;
        this.aPlace = aPlace;
        this.bPlace = bPlace;
    }

    @Override
    public String toString()
    {
        return "Flight: {id = '" + id + "'}, {aPlace: '" + aPlace + "'}, {bPlace: '" + bPlace + "'}";
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }

        Flight flight = (Flight) o;

        if(id != flight.id)
        {
            return false;
        }
        if(aPlace != null ? ! aPlace.equals(flight.aPlace) : flight.aPlace != null)
        {
            return false;
        }
        return bPlace != null ? bPlace.equals(flight.bPlace) : flight.bPlace == null;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (aPlace != null ? aPlace.hashCode() : 0);
        result = 31 * result + (bPlace != null ? bPlace.hashCode() : 0);
        return result;
    }
}
