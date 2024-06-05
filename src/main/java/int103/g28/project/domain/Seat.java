package int103.g28.project.domain;

import java.io.Serializable;

public class Seat implements Serializable {
    private static final long serialVersionUID = 1L;
    private String seatid;
    private boolean isBooked;
    private int rowofallseat;
    private int columnofallseat;
    // Constructor

    public Seat(String seatid) {
        this.seatid = seatid;
        this.isBooked = false;
    }

    //getters and setters

    public String getSeatid() {
        return seatid;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    public int getRowofallseat() {
        return rowofallseat;
    }

    public void setRowofallseat(int rowofallseat) {
        this.rowofallseat = rowofallseat;
    }

    public int getColumnofallseat() {
        return columnofallseat;
    }

    public void setColumnofallseat(int columnofallseat) {
        this.columnofallseat = columnofallseat;
    }

    //toString

    @Override
    public String toString() {
        return "Seat{" + "seatid=" + seatid + ", isBooked=" + isBooked + '}';
    }
}
