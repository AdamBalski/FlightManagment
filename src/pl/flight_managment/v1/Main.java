package pl.flight_managment.v1;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends JFrame
{
    JButton searchButton;

    JTextField aPlaceTextField, bPlaceTextField, seatsTextField;
    JLabel aPlaceLabel, bPlaceLabel, seatsLabel;
    String aPlaceBufor, bPlaceBufor;
    int seatsBufor;

    ArrayList<Flight> flights;
    DataBaseConnection dbc;

    public static void main(String[] args) throws SQLException
    {
        Main main = new Main();
    }

    private Main() throws SQLException
    {
        aPlaceBufor = "";
        bPlaceBufor = "";
        seatsBufor = 0;

        flights = new ArrayList<>();
        dbc = new DataBaseConnection();
        refresh();

        makeGUI();
    }

    private void makeGUI()
    {
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Flight searching");
        setResizable(false);
        setBounds(70, 40,  1000, 700);

        ImageIcon icon = new ImageIcon("src/images/search_img.jpg");
        searchButton = new JButton(icon);
        searchButton.setBounds(940, 10, 50, 50);
        this.add(searchButton);

        Font font = new Font(Font.SANS_SERIF, Font.BOLD,15);

        aPlaceLabel = new JLabel("Whence?:");
        bPlaceLabel = new JLabel("Whither?:");
        seatsLabel = new JLabel("How many seats?:");
        aPlaceLabel.setFont(font);
        bPlaceLabel.setFont(font);
        seatsLabel.setFont(font);
        aPlaceLabel.setBounds(10, 10, 75, 15);
        bPlaceLabel.setBounds(200, 10, 75, 15);
        seatsLabel.setBounds(380, 10, 130, 15);
        this.add(aPlaceLabel);
        this.add(bPlaceLabel);
        this.add(seatsLabel);

        font = new Font(Font.SANS_SERIF, Font.BOLD, 12);

        aPlaceTextField = new JTextField();
        bPlaceTextField = new JTextField();
        seatsTextField = new JTextField();
        aPlaceTextField.setFont(font);
        bPlaceTextField.setFont(font);
        seatsTextField.setFont(font);
        aPlaceTextField.setBounds(90, 10, 90, 15);
        bPlaceTextField.setBounds(280, 10, 90, 15);
        seatsTextField.setBounds(520, 10, 30, 15);
        this.add(aPlaceTextField);
        this.add(bPlaceTextField);
        this.add(seatsTextField);

        aPlaceLabel.setVisible(true);
        bPlaceLabel.setVisible(true);
        seatsLabel.setVisible(true);
        searchButton.setVisible(true);
        setVisible(true);

        //////
        FlightModule module = new FlightModule(new Flight(1, "Skoczow", "Gorki Wielkie"));
        module.setBounds(100, 100, 800, 400);
        module.setVisible(true);
        add(module);
    }

    private void refresh() throws SQLException
    {
        flights = dbc.getRecords();
    }
}
