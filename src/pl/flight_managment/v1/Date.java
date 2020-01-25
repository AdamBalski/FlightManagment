package pl.flight_managment.v1;

public class Date
{
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year)
    {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(String str)
    {
        day = 0;
        month = 0;
        year = 0;

        int colons = 0;
        for(int i = 0; i < str.length(); i++)
        {
            if(str.charAt(i) == ':')
            {
                colons++;
            }
            else
            {
                switch(colons)
                {
                    case 0:
                    {
                        day *= 10;
                        day += (int) str.charAt(i) - 48;
                        break;
                    }
                    case 1:
                    {
                        month *= 10;
                        month += (int) str.charAt(i) - 48;
                        break;
                    }
                    case 2:
                    {
                        year *= 10;
                        year += (int) str.charAt(i) - 48;
                        break;
                    }
                }
            }
        }
    }

    public boolean matches(DateRange dateRange)
    {
        return Date.compare(dateRange.getStart(), this) < 1 &&
                Date.compare(dateRange.getEnd(), this) > -1;
    }

    public int getDay()
    {
        return day;
    }

    public int getMonth()
    {
        return month;
    }

    public int getYear()
    {
        return year;
    }

    /**
     * Compares two dates
     * @return a before b - '-1', a equals b - '0', a after b - '1';
     */
    public static int compare(Date a, Date b)
    {
        if(a.getYear() < b.getYear())
        {
            return -1;
        }
        else if(b.getYear() < a.getYear())
        {
            return 1;
        }

        if(a.getMonth() < b.getMonth())
        {
            return -1;
        }
        else if(b.getMonth() < a.getMonth())
        {
            return 1;
        }

        if(a.getDay() < b.getDay())
        {
            return -1;
        }
        else if(b.getDay() < a.getDay())
        {
            return 1;
        }

        return 0;
    }

    @Override
    public String toString()
    {
        return String.valueOf(day) + ':' + month + ':' + year;
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

        Date date = (Date) o;

        if(day != date.day)
        {
            return false;
        }
        if(month != date.month)
        {
            return false;
        }
        return year == date.year;
    }
}
