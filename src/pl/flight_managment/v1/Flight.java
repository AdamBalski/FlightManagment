package pl.flight_managment.v1;

public class Flight
{
    int id;
    String aPlace;
    String bPlace;
    int seats;
    int free_seats;

    public Flight(int id, String aPlace, String bPlace, int seats, int free_seats)
    {
        this.id = id;
        this.aPlace = aPlace;
        this.bPlace = bPlace;
        this.seats = seats;
        this.free_seats = free_seats;
    }

    @Override
    public String toString()
    {
        return "Flight{" + "id=" + id + ", aPlace='" + aPlace + '\'' + ", bPlace='" + bPlace + '\'' + ", seats=" + seats + ", free_seats=" + free_seats + '}';
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
        if(seats != flight.seats)
        {
            return false;
        }
        if(free_seats != flight.free_seats)
        {
            return false;
        }
        if(! aPlace.equals(flight.aPlace))
        {
            return false;
        }
        return bPlace.equals(flight.bPlace);
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + aPlace.hashCode();
        result = 31 * result + bPlace.hashCode();
        result = 31 * result + seats;
        result = 31 * result + free_seats;
        return result;
    }
}
