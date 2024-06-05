package int103.g28.project.repository;

import int103.g28.project.domain.Seat;
import int103.g28.project.domain.Showtime;
import int103.g28.project.domain.Ticket;
import int103.g28.project.exception.TicketAlreadyException;
import int103.g28.project.exception.TicketNotFoundException;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JdbcTicketRepository implements TicketRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Ticket> tickets;
    private int ticketid = 0;
    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://int103g28-int103g28.f.aivencloud.com:12917/int103";
    private static final String JDBC_USER = "avnadmin";
    private static final String JDBC_PASSWORD = "AVNS_xZ6CQNAfHG1K7lPMpvi";
    private static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    // Constructor

    public JdbcTicketRepository() {
        this.tickets = new HashMap<>();
        try {
            Class.forName(JDBC_DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found!");
        }
        loadFromDB();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    // Load data from database
    private void loadFromDB() {
        try (Connection conn = connect()) {
            // Check if tables not exist
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS tickets (ticketid VARCHAR(10) PRIMARY KEY, showtime BLOB, seats BLOB)");
            }

            // Load tickets
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM tickets")) {
                while (rs.next()) {
                    Showtime showtime = deserialize(rs.getBytes("showtime"));
                    Seat seat = deserialize(rs.getBytes("seats"));
                    Ticket ticket = new Ticket(rs.getString("ticketid"), showtime, seat);
                    add(ticket);
                    ticketid = Integer.parseInt(rs.getString("ticketid"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save data to database
    private void saveToDB() {
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);

            // Save tickets
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO tickets (ticketid, showtime, seats) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE showtime=VALUES(showtime), seats=VALUES(seats)")) {
                for (Ticket ticket : getTickets().values()) {
                    pstmt.setString(1, ticket.getTicketid());
                    pstmt.setBytes(2, serialize(ticket.getShowtime()));
                    pstmt.setBytes(3, serialize(ticket.getSeats()));
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private byte[] serialize(Object obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> T deserialize(byte[] data) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Check if ticket already exists
    @Override
    public boolean check(Ticket ticket) {
        return tickets.containsKey(ticket.getTicketid());
    }

    // Methods
    @Override
    public String ticketnextId() {
        ticketid++;
        saveToDB();
        return String.valueOf(ticketid);
    }

    @Override
    public void add(Ticket ticket) {
        if (check(ticket)) {
            throw new TicketAlreadyException("Ticket already exists!");
        }
        tickets.put(ticket.getTicketid(), ticket);
        saveToDB();
    }

    @Override
    public void remove(Ticket ticket) {
        if (check(ticket)) {
            tickets.remove(ticket.getTicketid());
            saveToDB();
        } else {
            throw new TicketNotFoundException("Ticket not found!");
        }
    }

    @Override
    public Ticket find(String ticketid) {
        if (tickets.containsKey(ticketid)) {
            return tickets.get(ticketid);
        } else {
            throw new TicketNotFoundException("Ticket not found!");
        }
    }

    @Override
    public void update(Ticket ticket) {
        if (check(ticket)) {
            tickets.put(ticket.getTicketid(), ticket);
            saveToDB();
        } else {
            throw new TicketNotFoundException("Ticket not found!");
        }
    }

    @Override
    public Map<String, Ticket> getTickets() {
        return tickets;
    }
}
