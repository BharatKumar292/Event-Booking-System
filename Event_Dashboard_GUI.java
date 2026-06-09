import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Event_Dashboard_GUI extends JFrame {

    Connection con;

    JTextArea displayArea;

    // ================= CONSTRUCTOR =================
    public Event_Dashboard_GUI() {

        // DB CONNECTION
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/event_system",
                "root",
                "Bharat@#23"
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        // ================= FRAME =================
        setTitle("EVENT BOOKING SYSTEM - DASHBOARD");
        setSize(900, 550);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245));

        // ================= SIDEBAR PANEL =================
        JPanel sideBar = new JPanel();
        sideBar.setBounds(0, 0, 220, 550);
        sideBar.setBackground(new Color(30, 30, 60));
        sideBar.setLayout(null);

        JLabel title = new JLabel("EVENT SYSTEM");
        title.setForeground(Color.WHITE);
        title.setBounds(40, 20, 150, 30);
        sideBar.add(title);

        // BUTTONS
        JButton b1 = createButton("Add Event", 70);
        JButton b2 = createButton("View Events", 120);
        JButton b3 = createButton("Book Seat", 170);
        JButton b4 = createButton("View Bookings", 220);
        JButton b5 = createButton("Cancel Booking", 270);
        JButton b6 = createButton("Check Seats", 320);

        sideBar.add(b1);
        sideBar.add(b2);
        sideBar.add(b3);
        sideBar.add(b4);
        sideBar.add(b5);
        sideBar.add(b6);

        add(sideBar);

        // ================= MAIN PANEL =================
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(230, 10, 640, 500);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(null);

        JLabel header = new JLabel("Dashboard Output");
        header.setBounds(20, 10, 300, 30);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(header);

        displayArea = new JTextArea();
        JScrollPane sp = new JScrollPane(displayArea);
        sp.setBounds(20, 50, 590, 420);
        mainPanel.add(sp);

        add(mainPanel);

        // ================= ACTIONS =================
        b1.addActionListener(e -> addEvent());
        b2.addActionListener(e -> viewEvents());
        b3.addActionListener(e -> bookSeat());
        b4.addActionListener(e -> viewBookings());
        b5.addActionListener(e -> cancelBooking());
        b6.addActionListener(e -> checkSeats());

        setVisible(true);
    }

    // ================= BUTTON DESIGN =================
    JButton createButton(String text, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(20, y, 180, 35);
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    // ================= ADD EVENT =================
    void addEvent() {
        try {
            String name = JOptionPane.showInputDialog("Event Name:");
            int seats = Integer.parseInt(JOptionPane.showInputDialog("Total Seats:"));
            double price = Double.parseDouble(JOptionPane.showInputDialog("Price:"));
            String date = JOptionPane.showInputDialog("Date (YYYY-MM-DD):");

            String sql = "INSERT INTO events(event_name,total_seats,price,event_date) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, seats);
            ps.setDouble(3, price);
            ps.setString(4, date);

            ps.executeUpdate();

            displayArea.setText("✔ Event Added Successfully!");

        } catch (Exception e) {
            displayArea.setText(e.getMessage());
        }
    }

    // ================= VIEW EVENTS =================
    void viewEvents() {
        try {
            displayArea.setText("");

            ResultSet rs = con.prepareStatement("SELECT * FROM events").executeQuery();

            while (rs.next()) {
                displayArea.append(
                    "ID: " + rs.getInt("id") +
                    " | " + rs.getString("event_name") +
                    " | Seats: " + rs.getInt("total_seats") +
                    " | Price: " + rs.getDouble("price") +
                    " | Date: " + rs.getString("event_date") + "\n"
                );
            }

        } catch (Exception e) {
            displayArea.setText(e.getMessage());
        }
    }

    // ================= VIEW BOOKINGS =================
    void viewBookings() {
        try {
            displayArea.setText("");

            ResultSet rs = con.prepareStatement("SELECT * FROM bookings").executeQuery();

            while (rs.next()) {
                displayArea.append(
                    "ID: " + rs.getInt("id") +
                    " | Name: " + rs.getString("user_name") +
                    " | Event: " + rs.getInt("event_id") +
                    " | Seat: " + rs.getInt("seat_number") +
                    " | Date: " + rs.getString("booking_date") + "\n"
                );
            }

        } catch (Exception e) {
            displayArea.setText(e.getMessage());
        }
    }

    // ================= CANCEL BOOKING =================
    void cancelBooking() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Booking ID:"));

            PreparedStatement ps = con.prepareStatement("DELETE FROM bookings WHERE id=?");
            ps.setInt(1, id);

            int r = ps.executeUpdate();

            displayArea.setText(r > 0 ? " Booking Cancelled" : " Not Found");

        } catch (Exception e) {
            displayArea.setText(e.getMessage());
        }
    }

    // ================= CHECK SEATS =================
    void checkSeats() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Event ID:"));

            PreparedStatement ps1 = con.prepareStatement("SELECT total_seats FROM events WHERE id=?");
            ps1.setInt(1, id);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                displayArea.setText("Event not found!");
                return;
            }

            int total = rs1.getInt(1);

            PreparedStatement ps2 = con.prepareStatement("SELECT COUNT(*) FROM bookings WHERE event_id=?");
            ps2.setInt(1, id);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();

            int booked = rs2.getInt(1);

            displayArea.setText(
                "Total: " + total +
                "\nBooked: " + booked +
                "\nAvailable: " + (total - booked)
            );

        } catch (Exception e) {
            displayArea.setText(e.getMessage());
        }
    }

    // ================= BOOK SEAT =================

void bookSeat() {
    try {
        int eventId = Integer.parseInt(JOptionPane.showInputDialog("Enter Event ID:"));

        // GET EVENT INFO (same as your original)
        String ev = "SELECT event_name, price, event_date, total_seats FROM events WHERE id=?";
        PreparedStatement eps = con.prepareStatement(ev);
        eps.setInt(1, eventId);
        ResultSet ers = eps.executeQuery();

        if (!ers.next()) {
            displayArea.setText("Event not found!");
            return;
        }

        String eventName = ers.getString("event_name");
        double price = ers.getDouble("price");
        String eventDate = ers.getString("event_date");
        int totalSeats = ers.getInt("total_seats");

        // ORIGINAL DATE VALIDATION (FIXED BACK)
        LocalDate today = LocalDate.now();
        LocalDate eventDateValue = LocalDate.parse(eventDate);

        if (!eventDateValue.isAfter(today)) {
            displayArea.setText("Booking Failed! Event date has already passed or is today.");
            return;
        }

        // GET BOOKED SEATS (same logic)
        String seatQuery = "SELECT seat_number FROM bookings WHERE event_id=?";
        PreparedStatement sps = con.prepareStatement(seatQuery);
        sps.setInt(1, eventId);
        ResultSet srs = sps.executeQuery();

        ArrayList<Integer> bookedList = new ArrayList<>();

        while (srs.next()) {
            bookedList.add(srs.getInt("seat_number"));
        }

        int availableSeats = totalSeats - bookedList.size();

        if (availableSeats == 0) {
            displayArea.setText("Event FULL!");
            return;
        }

        int count = Integer.parseInt(JOptionPane.showInputDialog("How many seats you want:"));

        if (count > availableSeats) {
            displayArea.setText("Not enough seats available!");
            return;
        }

        StringBuilder result = new StringBuilder();
        double totalBill = 0;

        for (int i = 1; i <= count; i++) {

            String name = JOptionPane.showInputDialog("Enter Name for Seat " + i);
            String date = LocalDate.now().toString();

            // AUTO SEAT ASSIGNMENT (same logic)
            int seat = -1;

            for (int s = 1; s <= totalSeats; s++) {
                if (!bookedList.contains(s)) {
                    seat = s;
                    break;
                }
            }

            if (seat == -1) break;

            // INSERT BOOKING (same as original)
            String ins = "INSERT INTO bookings(user_name, event_id, seat_number, status, booking_date) VALUES (?, ?, ?, 'Booked', ?)";
            PreparedStatement ips = con.prepareStatement(ins);

            ips.setString(1, name);
            ips.setInt(2, eventId);
            ips.setInt(3, seat);
            ips.setString(4, date);

            ips.executeUpdate();

            bookedList.add(seat);
            totalBill += price;

            int ticketId = eventId * 1000 + seat;

            // TICKET FORMAT (same as your original logic)
            result.append(
                "\n==============================\n" +
                " EVENT TICKET\n" +
                "==============================\n" +
                " Ticket ID: " + ticketId + "\n" +
                " Name: " + name + "\n" +
                " Event: " + eventName + "\n" +
                " Seat: " + seat + "\n" +
                " Price: Rs " + price + "\n" +
                " Event Date: " + eventDate + "\n" +
                " Booking Date: " + date + "\n" +
                " Status: CONFIRMED\n"
            );
        }

        result.append("\nTOTAL BILL: Rs " + totalBill);

        displayArea.setText(result.toString());

    } catch (Exception e) {
        displayArea.setText(e.getMessage());
    }
}
    // ================= MAIN =================
    public static void main(String[] args) {
        new Event_Dashboard_GUI();
    }
}