package pl.flight_managment.v1;

import javax.swing.*;

public class FlightModule extends JPanel
{
    Flight flight;
    JLabel info;

    public FlightModule(Flight flight)
    {
        super(null);

        info = new JLabel("<html>asdfd</html>");
       // info = new JLabel(
         //       "<html>  Flight's id: 31 </html>"
        //);
//        info = new JLabel(
//                "<html>" +
//                        "<body>" +
//                        "<h6>" +
//                        "Flight's id: " + flight.id +
//                        "<br>   Whence: " + flight.aPlace +
//                        "   Whither: " + flight.bPlace +
//                        "<br>   Seats: " + flight.seats +
//                        "   Free seats: " + flight.free_seats +
//                        "</h6>" +
//                        "</body>" +
//                     "</html>"
//        );
        info.setBounds(0, 0, 800, 400);
        add(info);

        info.setVisible(true);
        setVisible(true);
    }
}
