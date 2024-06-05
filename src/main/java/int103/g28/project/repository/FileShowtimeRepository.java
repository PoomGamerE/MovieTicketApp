package int103.g28.project.repository;

import int103.g28.project.domain.Seat;
import int103.g28.project.domain.Showtime;
import int103.g28.project.exception.ShowtimeAlreadyException;
import int103.g28.project.exception.ShowtimeNotFoundException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileShowtimeRepository implements ShowtimeRepository, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Showtime> showtimes;
    private int showtimeid = 0;
    private static final String DATA_FILE = "ShowtimeRepository.dat";

    // Constructor

    public FileShowtimeRepository() {
        this.showtimes = new HashMap<>();
        loadFromFile();
    }

    // Load data from file
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            createEmptyFile(file);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            showtimes = (Map<String, Showtime>) ois.readObject();
            showtimeid = ois.readInt();
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found, starting with an empty repository.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save data to file
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(showtimes);
            oos.writeInt(showtimeid);
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

    // Check if showtime already exists
    @Override
    public boolean check(Showtime showtime) {
        return showtimes.containsKey(showtime.getShowtimeid());
    }

    // Methods
    @Override
    public String showtimenextId() {
        showtimeid++;
        saveToFile();
        return String.valueOf(showtimeid);
    }

    @Override
    public void add(Showtime showtime) {
        if (check(showtime)) {
            throw new ShowtimeAlreadyException("Showtime already exists!");
        }
        showtimes.put(showtime.getShowtimeid(), showtime);
        saveToFile();
    }

    @Override
    public void remove(Showtime showtime) {
        if (check(showtime)) {
            showtimes.remove(showtime.getShowtimeid());
            saveToFile();
        } else {
            throw new ShowtimeNotFoundException("Showtime not found!");
        }
    }

    @Override
    public Showtime find(String showtimeid) {
        if (showtimes.containsKey(showtimeid)) {
            return showtimes.get(showtimeid);
        } else {
            throw new ShowtimeNotFoundException("Showtime not found!");
        }
    }

    @Override
    public void update(Showtime showtime) {
        if (check(showtime)) {
            showtimes.put(showtime.getShowtimeid(), showtime);
            saveToFile();
        } else {
            throw new ShowtimeNotFoundException("Showtime not found!");
        }
    }

    @Override
    public Map<String, Showtime> getAll() {
        return showtimes;
    }

    @Override
    public void setAll(Map<String, Showtime> showtimes) {
        this.showtimes = showtimes;
        saveToFile();
    }

    @Override
    public Map<String, Seat> getSeats(String showtimeid) {
        return showtimes.get(showtimeid).getSeats();
    }

    @Override
    public Seat getSeat(String showtimeid, String seatid) {
        return showtimes.get(showtimeid).getSeats().get(seatid);
    }
}
