package pl.flight_managment.v1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends JFrame implements ActionListener
{
    JButton searchButton;

    JTextField aPlaceTextField, bPlaceTextField, startDateTextField, endDateTextField, seatsTextField;
    JLabel aPlaceLabel, bPlaceLabel, startDateLabel, endDateLabel, seatsLabel;
    int page;
    boolean isFavorite;
    JLabel[] info;
    Flight[] flightsOnPage;
    JButton[] flightFavoriteButtons;
    JButton previous, next, favoriteButton;

    ArrayList<Flight> flights;
    DataBaseConnection dbc;

    public static void main(String[] args) throws SQLException
    {
        Main main = new Main();
        main.makeGUI();
        new Date("25:01:2020");
    }

    private void makeGUI()
    {
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Flight search");
        setResizable(false);
        setBounds(70, 40,  1000, 700);

        ImageIcon icon = new ImageIcon("src/images/search_img.jpg");
        searchButton = new JButton(icon);
        searchButton.setBounds(940, 10, 50, 50);
        this.add(searchButton);
        searchButton.addActionListener(this);

        Font font = new Font(Font.SANS_SERIF, Font.BOLD,15);

        aPlaceLabel = new JLabel("Whence?:");
        bPlaceLabel = new JLabel("Whither?:");
        startDateLabel = new JLabel("From(date)?:");
        endDateLabel = new JLabel("To(date)?:");
        seatsLabel = new JLabel("How many seats?:");
        aPlaceLabel.setFont(font);
        bPlaceLabel.setFont(font);
        startDateLabel.setFont(font);
        endDateLabel.setFont(font);
        seatsLabel.setFont(font);
        aPlaceLabel.setBounds(10, 10, 75, 15);
        bPlaceLabel.setBounds(200, 10, 75, 15);
        startDateLabel.setBounds(10, 30, 95, 15);
        endDateLabel.setBounds(200, 30, 75, 15);
        seatsLabel.setBounds(380, 10, 130, 15);
        this.add(aPlaceLabel);
        this.add(bPlaceLabel);
        this.add(startDateLabel);
        this.add(endDateLabel);
        this.add(seatsLabel);

        font = new Font(Font.SANS_SERIF, Font.BOLD, 12);

        aPlaceTextField = new JTextField();
        bPlaceTextField = new JTextField();
        startDateTextField = new JTextField();
        endDateTextField = new JTextField();
        seatsTextField = new JTextField();
        aPlaceTextField.setFont(font);
        bPlaceTextField.setFont(font);
        startDateTextField.setFont(font);
        endDateTextField.setFont(font);
        seatsTextField.setFont(font);
        aPlaceTextField.setBounds(90, 10, 90, 15);
        bPlaceTextField.setBounds(280, 10, 90, 15);
        startDateTextField.setBounds(110, 30, 70, 15);
        endDateTextField.setBounds(280, 30, 90, 15);
        seatsTextField.setBounds(520, 10, 30, 15);
        this.add(aPlaceTextField);
        this.add(bPlaceTextField);
        this.add(startDateTextField);
        this.add(endDateTextField);
        this.add(seatsTextField);

        aPlaceLabel.setVisible(true);
        bPlaceLabel.setVisible(true);
        startDateLabel.setVisible(true);
        endDateLabel.setVisible(true);
        seatsLabel.setVisible(true);
        searchButton.setVisible(true);

        ImageIcon previousIcon = new ImageIcon("src/images/previous_img.jpg");
        ImageIcon nextIcon = new ImageIcon("src/images/next_img.jpg");
        previous = new JButton(previousIcon);
        next = new JButton(nextIcon);
        previous.setBounds(30, 8*70+50, 50, 50);
        this.add(previous);
        previous.addActionListener(this);
        next.setBounds(85, 8*70+50, 50, 50);
        this.add(next);
        next.addActionListener(this);
        previous.setEnabled(false);
        next.setEnabled(false);

        icon = new ImageIcon("src/images/not_favorite_img.jpg");
        favoriteButton = new JButton(icon);
        favoriteButton.setBounds(565, 10, 50, 50);
        this.add(favoriteButton);
        favoriteButton.addActionListener(this);

        setVisible(true);

        for(int i = 0; i < 8; i++)
        {
            info[i] = new JLabel();
            add(info[i]);
            info[i].setBounds(30, 40 + ((i) * 70), 800, 70);
            info[i].setVisible(true);

            flightFavoriteButtons[i] = new JButton();
            add(flightFavoriteButtons[i]);
            flightFavoriteButtons[i].setBounds(960, 80 + ((i) * 70), 20, 20);
            flightFavoriteButtons[i].setVisible(false);
            flightFavoriteButtons[i].addActionListener(this);
        }
    }

    private Main() throws SQLException
    {
        flights = new ArrayList<>();
        dbc = new DataBaseConnection();

        isFavorite = false;
        page = 0;
        info = new JLabel[8];
        flightsOnPage = new Flight[8];
        flightFavoriteButtons = new JButton[8];
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object obj = e.getSource();

        if(obj == searchButton)
        {
            search();
        }
        else if(obj == previous)
        {
            previousPage();
        }
        else if(obj == next)
        {
            nextPage();
        }
        else if(obj == favoriteButton)
        {
            setFavorite(!isFavorite);
        }
        else if(favoriteFlightButtonIndex(obj) != - 1)
        {
            setFlightReversedFavoriteByButton(obj);
        }
    }

    private void search()
    {
        try
        {
            refresh(getFlightFromOutput(), getDateRangeFromOutput());
            page = 0;
            drawPage();
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("You typed something wrong. Please repeat and write right data.");
        }
        catch(SQLException sqle)
        {
            System.out.println("Something is wrong with our database. Sorry try again later.");
        }
    }

    private Flight getFlightFromOutput()
    {
        try
        {
            String aPlace = aPlaceTextField.getText();
            String bPlace = bPlaceTextField.getText();

            String str = seatsTextField.getText();
            int seats = str.equals("") ? 0 : Integer.parseInt(str);
            return new Flight(Integer.MIN_VALUE, aPlace, bPlace, new Date("0:0:0"), Integer.MIN_VALUE, seats, isFavorite);
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("You typed something wrong. Please repeat and write right data.");
        }

        System.out.println("Something went wrong.");
        return new Flight(-1, "", "", new Date("0:0:0"),0, 0, isFavorite);
    }

    private DateRange getDateRangeFromOutput()
    {
        try
        {
            String start = aPlaceTextField.getText();
            String end = bPlaceTextField.getText();

            return new DateRange(start, end);
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("You typed something wrong. Please repeat and write right data.");
        }

        System.out.println("Something went wrong.");
        return new DateRange("0:0:0", "0:0:0");
    }

    private void nextPage()
    {
        page++;
        drawPage();
    }

    private void previousPage()
    {
        page--;
        drawPage();
    }

    private void setFlightReversedFavoriteByButton(Object obj)
    {
        int position = favoriteFlightButtonIndex(obj);
        JButton button = flightFavoriteButtons[position];
        Flight flight = flightsOnPage[position];

        dbc.setFavorite(flight.getId(), !flight.isFavorite);
        flight.isFavorite = !flight.isFavorite;

        String path = flight.isFavorite ? "src/images/favorite_mini_img.jpg" :
                "src/images/not_favorite_mini_img.jpg";
        button.setIcon(new ImageIcon(path));

        System.out.println(position);
    }

    private int favoriteFlightButtonIndex(Object obj)
    {
        if(!(obj instanceof JButton))
            return -1;

        JButton button = (JButton) obj;
        for(int i = 0; i < 8; i++)
            if(button == flightFavoriteButtons[i])
                return i;

        return -1;
    }

    private void setFavorite(boolean flag)
    {
        isFavorite = flag;

        String path;
        if(isFavorite)
            path = "src/images/favorite_img.jpg";
        else
            path = "src/images/not_favorite_img.jpg";

        favoriteButton.setIcon(new ImageIcon(path));
    }

    private boolean onLastPage()
    {
        return 8 * (page + 1) >= flights.size();
    }

    private void refresh() throws SQLException
    {
        flights = dbc.getRecords();
    }

    private void refresh(Flight filter, DateRange dataRange) throws SQLException
    {
        flights = dbc.getRecords(filter, dataRange);
    }

    private void drawPage()
    {
        int j = 0;
        for(int i = page * 8; j < 8 && i < flights.size(); i++, j++)
        {
            createFlightModule(flights.get(i), j);
        }

        for(; j < 8; j++)
        {
            info[j].setText("");
            flightsOnPage[j] = null;
            flightFavoriteButtons[j].setVisible(false);
        }

        setSwitchingPagesAbility();
    }

    private void setSwitchingPagesAbility()
    {
        previous.setEnabled(page != 0);
        next.setEnabled(!onLastPage());
    }

    private void createFlightModule(Flight flight, int position)
    {
        info[position].setText(
                "<html>" +
                        "<h2>" +
                        flight.aPlace +
                        " - " +
                        flight.bPlace +
                        "</h2>" +

                        "Free seats: " +
                        flight.free_seats +

                        "<br>" +
                        "Flight's Id: " +
                        flight.getId() +
                        "</html>"
        );

        flightsOnPage[position] = flight;

        JButton favorite = flightFavoriteButtons[position];

        String path = flight.isFavorite ? "src/images/favorite_mini_img.jpg" :
                "src/images/not_favorite_mini_img.jpg";
        favorite.setIcon(new ImageIcon(path));

        favorite.setVisible(true);
    }
}
