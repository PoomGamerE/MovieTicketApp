package int103.g28.project.repository;

import int103.g28.project.domain.Ticket;
import int103.g28.project.exception.TicketAlreadyException;
import int103.g28.project.exception.TicketNotFoundException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileTicketRepository implements TicketRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Ticket> tickets;
    private int ticketid = 0;
    private static final String DATA_FILE = "TicketRepository.dat";

    // Constructor

    public FileTicketRepository() {
        this.tickets = new HashMap<>();
        loadFromFile();
    }

    // Load data from file
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            createEmptyFile(file);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            tickets = (Map<String, Ticket>) ois.readObject();
            ticketid = ois.readInt();
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found, starting with an empty repository.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save data to file
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(tickets);
            oos.writeInt(ticketid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create an empty file if it does not exist
    private void createEmptyFile(File file) {
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                saveToFile();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            System.out.println("Please move the location of the file to another path");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
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
        saveToFile();
        return String.valueOf(ticketid);
    }

    @Override
    public void add(Ticket ticket) {
        if (check(ticket)) {
            throw new TicketAlreadyException("Ticket already exists!");
        }
        tickets.put(ticket.getTicketid(), ticket);
        saveToFile();
    }

    @Override
    public void remove(Ticket ticket) {
        if (check(ticket)) {
            tickets.remove(ticket.getTicketid());
            saveToFile();
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
            saveToFile();
        } else {
            throw new TicketNotFoundException("Ticket not found!");
        }
    }

    @Override
    public Map<String, Ticket> getTickets() {
        return tickets;
    }
}
