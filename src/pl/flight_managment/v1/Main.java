package pl.flight_managment.v1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import pl.atomos.v1.*;

public class Main extends JFrame implements ActionListener
{
    JButton searchButton;

    JTextField aPlaceTextField, bPlaceTextField, seatsTextField;
    JLabel aPlaceLabel, bPlaceLabel, seatsLabel;

    String aPlaceBufor, bPlaceBufor;
    int seatsBufor;
    int page;
    JLabel[] info;
    JButton previous, next;

    ArrayList<Flight> flights;
    DataBaseConnection dbc;

    public static void main(String[] args) throws SQLException
    {
        Main main = new Main();

        main.refresh();

        main.drawPage();

        System.out.println(Atomos.longestSubsequence("fish", "fosh"));
        //TODO it has to print '3' instead of printed '2' so it's something wrong with the longest subsequence algorithm
    }

    private void drawPage()
    {
        int iReal = 0;

        for(int i = page * 8; iReal < 8 && i < flights.size(); i++)
        {
            if(satisfyingContraints(flights.get(i)))
            {
                createFlightModule(flights.get(i), iReal);
                iReal++;
            }
        }

        for(; iReal < 8; iReal++)
        {
            info[iReal].setText("");
        }
    }

    private Main() throws SQLException
    {
        aPlaceBufor = "";
        bPlaceBufor = "";
        seatsBufor = 0;

        flights = new ArrayList<>();
        dbc = new DataBaseConnection();

        aPlaceBufor = "";
        bPlaceBufor = "";
        seatsBufor = 0;

        page = 0;
        info = new JLabel[8];

        makeGUI();
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
                                flight.id +
                "</html>"
        );
    }

    private boolean satisfyingContraints(Flight flight)
    {
        return flight.free_seats >= seatsBufor;
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
        searchButton.addActionListener(this);

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


        setVisible(true);

        for(int i = 0; i < 8; i++)
        {
            info[i] = new JLabel();
            add(info[i]);
            info[i].setBounds(30, 40 + ((i) * 70), 800, 70);
            info[i].setVisible(true);
        }
    }

    private void refresh() throws SQLException
    {
        flights = dbc.getRecords();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object obj = e.getSource();

        if(obj == searchButton)
        {
            try
            {
                aPlaceBufor = aPlaceTextField.getText();
                bPlaceBufor = bPlaceTextField.getText();

                String str = seatsTextField.getText();
                seatsBufor = str.equals("") ? 0 : Integer.parseInt(str);

                refresh();


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
        else if(obj == previous)
        {
            if(page == 0)
            {
                System.out.println("You are on first page.");
            }
            else
            {
                page--;
                drawPage();
            }
        }
        else if(obj == next)
        {
            // if(page == last_page)

            page++;
            drawPage();
        }
    }
}
