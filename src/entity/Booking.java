package entity;

import java.util.List;

public class Booking {

    private int bookingId;
    private int userId;
    private int showId;
    private List<String> seatsBooked;
    private double totalPrice;

    public Booking(int bookingId, int userId, int showId, List<String> seatsBooked, int totalPrice) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.showId = showId;
        this.seatsBooked = seatsBooked;
        this.totalPrice = totalPrice;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public List<String> getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(List<String> seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
