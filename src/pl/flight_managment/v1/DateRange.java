package pl.flight_managment.v1;

public class DateRange
{
    private Date start;
    private Date end;

    public DateRange(String a, String b)
    {
        this(new Date(a), new Date(b));
    }

    public DateRange(Date a, Date b)
    {
        start = a;
        end = b;
    }

    public Date getStart()
    {
        return start;
    }

    public Date getEnd()
    {
        return end;
    }

    @Override
    public String toString()
    {
        return "DateRange{" + "start=" + start + ", end=" + end + '}';
    }
}
