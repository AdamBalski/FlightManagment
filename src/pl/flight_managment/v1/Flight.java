package pl.flight_managment.v1;

import pl.atomos.v1.Atomos;

public class Flight
{
    private int id;
    String aPlace;
    String bPlace;
    int seats;
    int free_seats;
    boolean isFavorite;

    public Flight(int id, String aPlace, String bPlace, int seats, int free_seats, boolean isFavorite)
    {
        this.id = id;
        this.aPlace = aPlace;
        this.bPlace = bPlace;
        this.seats = seats;
        this.free_seats = free_seats;
        this.isFavorite = isFavorite;
    }

    public boolean match(Flight flight)
    {
        if(flight.id != Integer.MIN_VALUE)
        {
            if(flight.id != this.id)    return false;
        }

        if(!flight.aPlace.equals("NOT_TO_BE_CONSIDERED"))
        {
            int lcs = Atomos.longestCommonSubsequence(flight.aPlace.toLowerCase(), this.aPlace.toLowerCase());
            int max_len = Math.max(this.aPlace.length(), flight.aPlace.length());

            if(lcs < max_len * 0.7) return false;
        }

        if(!flight.bPlace.equals("NOT_TO_BE_CONSIDERED"))
        {
            int lcs = Atomos.longestCommonSubsequence(flight.bPlace.toLowerCase(), this.bPlace.toLowerCase());
            int min_len = Math.min(this.bPlace.length(), flight.bPlace.length());

            if(lcs < min_len * 0.6) return false;
        }

        if(flight.seats != Integer.MIN_VALUE)
        {
            if(this.seats < flight.seats)   return false;
        }

        if(flight.free_seats != Integer.MIN_VALUE)
        {
            if(this.free_seats < flight.free_seats) return false;
        }

        if(flight.isFavorite)
        {
            if(!this.isFavorite)    return false;
        }

        return true;
    }

    public int getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return "Flight{" + "id=" + id + ", aPlace='" + aPlace + '\'' + ", bPlace='" + bPlace + '\'' + ", seats=" + seats + ", free_seats=" + free_seats + ", isFavorite=" + isFavorite + '}';
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
        if(isFavorite != flight.isFavorite)
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
        result = 31 * result + (isFavorite ? 11 : 7);
        return result;
    }
}
